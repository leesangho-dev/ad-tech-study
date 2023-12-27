package leesangho.adtechstudy.observation.webflux.service;

import leesangho.adtechstudy.observation.webflux.domain.ObservationDocument;
import leesangho.adtechstudy.observation.webflux.domain.ObservationDocumentRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@Slf4j
public class ObservationService {

  private static final String LOG_FORMAT = "ObservationService {}";

  private final ObservationDocumentRepository observationDocumentRepository;

  private final WebClient webClient;

  public ObservationService(ObservationDocumentRepository observationDocumentRepository,
      WebClient webClient) {
    this.observationDocumentRepository = observationDocumentRepository;
    this.webClient = webClient;
  }

  public Mono<String> basic() {
    log.info(LOG_FORMAT, "basic");
    return Mono.defer(() -> {
          log.info(LOG_FORMAT, "basic publisher");
          return Mono.just("basic");
        })
        .doOnNext(s -> log.info(LOG_FORMAT, "basic doOnNext"))
        .subscribeOn(Schedulers.boundedElastic())
        .log();
  }

  public Mono<String> clientError() {
    return Mono.error(() -> {
      log.info(LOG_FORMAT, "clientError publisher");
      return new IllegalArgumentException("클라이언트 에러 발생");
    });
  }

  public Mono<String> saveMongodb(String name) {
    log.info(LOG_FORMAT, "saveMongodb");
    return observationDocumentRepository.save(new ObservationDocument(name))
        .map(observationDocument -> {
          log.info(LOG_FORMAT, "saveMongodb map");
          return observationDocument.toString();
        });
  }

  public Mono<String> findMongodb(String id) {
    log.info(LOG_FORMAT, "findMongodb");
    return observationDocumentRepository.findById(new ObjectId(id))
        .map(ob -> {
          log.info(LOG_FORMAT, "findMongodb map");
          return id;
        })
        .log();
  }

  public Mono<String> restApiCall() {
    return webClient.get()
        .uri("http://localhost:8080/v3/api-docs/swagger-config")
        .retrieve()
        .bodyToMono(String.class)
        .doOnNext(s -> log.info(LOG_FORMAT, "restApiCall doOnNext"))
        .flatMap(Mono::just)
        .publishOn(Schedulers.boundedElastic())
        .subscribeOn(Schedulers.single())
        .log();
  }
}
