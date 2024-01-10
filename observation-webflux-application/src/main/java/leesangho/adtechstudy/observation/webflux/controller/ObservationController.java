package leesangho.adtechstudy.observation.webflux.controller;

import leesangho.adtechstudy.observation.webflux.service.ObservationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/observations")
@Slf4j
public class ObservationController {

    private static final String LOG_FORMAT = "ObservationController {}";

    private final ObservationService observationService;

    public ObservationController(ObservationService observationService) {
        this.observationService = observationService;
    }

    @GetMapping("/basic")
    public Mono<String> basic() {
        log.info(LOG_FORMAT, "basic");
        return observationService.basic();
    }

    @PostMapping("/mongodb")
    public Mono<String> saveMongodb(String name) {
        log.info(LOG_FORMAT, "saveMongodb");
        return observationService.saveMongodb(name);
    }

    @GetMapping("/mongodb/{id}")
    public Mono<String> findMongodb(@PathVariable String id) {
        log.info(LOG_FORMAT, "findMongodb");
        return observationService.findMongodb(id);
    }

    @GetMapping("/client-error")
    public Mono<String> clientError() {
        log.info(LOG_FORMAT, "clientError");
        return observationService.clientError();
    }

    @GetMapping("/server-error")
    public Mono<String> serverError() {
        log.info(LOG_FORMAT, "serverError");
        throw new RuntimeException("서버 에러 발생");
    }

    @GetMapping("/rest-api-call")
    public Mono<String> restApiCall() {
        log.info(LOG_FORMAT, "restApiCall");
        return observationService.restApiCall();
    }
}
