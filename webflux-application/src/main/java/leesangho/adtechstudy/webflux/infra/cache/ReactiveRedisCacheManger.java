package leesangho.adtechstudy.webflux.infra.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ReactiveRedisCacheManger implements ReactiveCacheManger {

    private static final Duration TTL = Duration.ofMinutes(5);

    private final ReactiveStringRedisTemplate reactiveStringRedisTemplate;

    private final ObjectMapper objectMapper;

    public ReactiveRedisCacheManger(ReactiveStringRedisTemplate reactiveStringRedisTemplate,
        ObjectMapper objectMapper) {
        this.reactiveStringRedisTemplate = reactiveStringRedisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> Mono<T> getCache(String key, TypeReference<T> typeReference) {
        log.info("reactiveRedisCacheManger.getCache: {}", Thread.currentThread().getName());
        return reactiveStringRedisTemplate.opsForValue()
            .get(key)
            .map(value -> convertStringToValue(typeReference, value))
            .log();
    }

    private <T> T convertStringToValue(TypeReference<T> typeReference, String value) {
        try {
            return objectMapper.readValue(value, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> void setCache(String key, T value) {
        log.info("reactiveRedisCacheManger.setCache: {}", Thread.currentThread().getName());
        reactiveStringRedisTemplate.opsForValue()
            .set(key, convertValueToString(value), TTL)
            .log()
            .subscribe();
    }

    private <T> String convertValueToString(T value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
