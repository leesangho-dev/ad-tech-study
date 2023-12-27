package leesangho.adtechstudy.observation.mvc.service;

import leesangho.adtechstudy.observation.mvc.domain.ObservationDocument;
import leesangho.adtechstudy.observation.mvc.domain.ObservationDocumentRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class ObservationService {

  private static final String LOG_FORMAT = "ObservationService {}";

  private final ObservationDocumentRepository observationDocumentRepository;

  private final RestTemplate restTemplate;

  private final RestClient restClient;

  public ObservationService(ObservationDocumentRepository observationDocumentRepository,
      RestTemplate restTemplate, RestClient restClient) {
    this.observationDocumentRepository = observationDocumentRepository;
    this.restTemplate = restTemplate;
    this.restClient = restClient;
  }

  public void basic() {
    log.info(LOG_FORMAT, "basic");
  }

  @Async
  public void async() {
    log.info(LOG_FORMAT, "async");
  }

  public void saveMongodb(String name) {
    log.info(LOG_FORMAT, "saveMongodb");
    observationDocumentRepository.save(new ObservationDocument(name));
  }

  public void findMongodb(ObjectId id) {
    log.info(LOG_FORMAT, "findMongodb");
    observationDocumentRepository.findById(id)
        .ifPresent(observationDocument -> log.info("{}", observationDocument));
  }

  public void restApiCallByTemplate() {
    log.info(LOG_FORMAT, "restApiCallByTemplate");
    restTemplate.getForEntity("http://localhost:8080/v3/api-docs/swagger-config", String.class);
  }

  public void restApiCallByClient() {
    log.info(LOG_FORMAT, "restApiCallByClient");
    restClient.get()
        .uri("http://localhost:8080/v3/api-docs/swagger-config")
        .retrieve()
        .body(String.class);
  }
}
