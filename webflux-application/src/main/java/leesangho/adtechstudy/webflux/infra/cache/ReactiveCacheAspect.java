package leesangho.adtechstudy.webflux.infra.cache;

import com.fasterxml.jackson.core.type.TypeReference;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


@Aspect
@Component
public class ReactiveCacheAspect {

    private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();

    private final ReactiveCacheManger reactiveCacheManger;

    public ReactiveCacheAspect(ReactiveCacheManger reactiveCacheManger) {
        this.reactiveCacheManger = reactiveCacheManger;
    }

    @Around("@annotation(ReactiveCacheable)")
    public Object reactiveCacheable(ProceedingJoinPoint proceedingJoinPoint) {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        ReactiveCacheable annotation = methodSignature.getMethod()
                .getAnnotation(ReactiveCacheable.class);

        String cacheKey = generateCacheKey(annotation, proceedingJoinPoint);
        TypeReference<Object> typeReference = getTypeReference(methodSignature.getMethod());

        return reactiveCacheManger.getCache(cacheKey, typeReference)
                .switchIfEmpty(Mono.defer(() -> cacheWrite(proceedingJoinPoint, cacheKey)));
    }

    private Mono<?> cacheWrite(ProceedingJoinPoint proceedingJoinPoint, String cacheKey) {
        try {
            return ((Mono<?>) proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs()))
                    .doOnSuccess(value -> reactiveCacheManger.setCache(cacheKey, value));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private String generateCacheKey(ReactiveCacheable annotation, ProceedingJoinPoint proceedingJoinPoint) {
        StandardEvaluationContext standardEvaluationContext = makeStandardEvaluationContext(proceedingJoinPoint);
        Object key = EXPRESSION_PARSER.parseExpression(annotation.key())
                .getValue(standardEvaluationContext, String.class);
        return annotation.value() + "::" + key;
    }

    private StandardEvaluationContext makeStandardEvaluationContext(ProceedingJoinPoint proceedingJoinPoint) {
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Parameter[] parameters = signature.getMethod().getParameters();
        for (int i = 0, parametersLength = parameters.length; i < parametersLength; i++) {
            Parameter parameter = parameters[i];
            standardEvaluationContext.setVariable(parameter.getName(), proceedingJoinPoint.getArgs()[i]);
        }
        return standardEvaluationContext;
    }

    private TypeReference<Object> getTypeReference(Method method) {
        return new TypeReference<>() {
            @Override
            public Type getType() {
                return getMethodActualReturnType(method);
            }
        };
    }

    private Type getMethodActualReturnType(Method method) {
        return ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0];
    }
}
