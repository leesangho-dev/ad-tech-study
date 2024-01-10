package leesangho.adtechstudy.webflux.infra.cache;

import com.fasterxml.jackson.core.type.TypeReference;
import reactor.core.publisher.Mono;

public interface ReactiveCacheManger {

    <T> Mono<T> getCache(String key, TypeReference<T> typeReference);

    <T> void setCache(String key, T value);
}
