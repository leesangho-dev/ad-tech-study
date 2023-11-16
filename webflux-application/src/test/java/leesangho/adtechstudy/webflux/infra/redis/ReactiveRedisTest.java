package leesangho.adtechstudy.webflux.infra.redis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReactiveRedisTest {

    ReactiveStringRedisTemplate reactiveStringRedisTemplate;

    StringRedisTemplate stringRedisTemplate;

    @BeforeEach
    void setUp() {
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory("localhost", 6379);
        lettuceConnectionFactory.afterPropertiesSet();
        reactiveStringRedisTemplate = new ReactiveStringRedisTemplate(lettuceConnectionFactory);
        stringRedisTemplate = new StringRedisTemplate(lettuceConnectionFactory);
    }

    @Nested
    class ReactiveTest {

        @DisplayName("비동기 레디스 동작테스트")
        @Test
        void reactive_redis_set_test() {
            // Given
            String key = "react-test";
            String value = "비동기 테스트";

            // When
            Mono<Boolean> publisher = reactiveStringRedisTemplate.opsForValue()
                    .set(key, value);
            // Then
            StepVerifier.create(publisher)
                    .expectNext(true)
                    .expectComplete()
                    .log()
                    .verify()
            ;

            // When & Then
            StepVerifier.create(reactiveStringRedisTemplate.opsForValue()
                            .get(key))
                    .expectNext(value)
                    .expectComplete()
                    .log()
                    .verify()
            ;

            // When & Then
            StepVerifier.create(reactiveStringRedisTemplate.opsForValue()
                            .delete(key))
                    .expectNext(true)
                    .expectComplete()
                    .log()
                    .verify()
            ;
        }

        @DisplayName("블로킹 레디스 테스트")
        @Test
        void blocking_redis_test() {
            // Given
            String key = "react-test";
            String value = "비동기 테스트";

            // When & Then
            assertThatThrownBy(() -> {
                Mono.delay(Duration.ofMillis(1))
                        .doOnNext(it -> {
                            stringRedisTemplate.opsForValue()
                                    .set(key, value);
                        })
                        .block();
            })
                    .hasMessageContaining("Blocking call")
            ;
        }

        @DisplayName("논블로킹 레디스 테스트")
        @Test
        void nonblocking_redis_test() {
            // Given
            String key = "react-test";
            String value = "비동기 테스트";

            // When & Then
            assertThatThrownBy(() -> {
                Mono.delay(Duration.ofMillis(1))
                        .flatMap(l -> reactiveStringRedisTemplate.opsForValue()
                                .set(key, value))
                        .block();
            })
                    .hasMessageContaining("Blocking call")
            ;
        }

    }

}
