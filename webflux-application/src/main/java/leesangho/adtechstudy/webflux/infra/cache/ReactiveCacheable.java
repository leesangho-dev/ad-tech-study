package leesangho.adtechstudy.webflux.infra.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ReactiveCacheable {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    String key() default "";
}
