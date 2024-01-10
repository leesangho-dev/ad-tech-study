package leesangho.adtechstudy.observation.mvc.controller;

import leesangho.adtechstudy.observation.mvc.service.ObservationService;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public void basic() {
        log.info(LOG_FORMAT, "basic");
        observationService.basic();
    }

    @PostMapping("/mongodb")
    public void saveMongodb(@RequestParam String name) {
        log.info(LOG_FORMAT, "saveMongodb");
        observationService.saveMongodb(name);
    }

    @GetMapping("/mongodb/{id}")
    public void findMongodb(@PathVariable String id) {
        log.info(LOG_FORMAT, "findMongodb");
        observationService.findMongodb(new ObjectId(id));
    }

    @GetMapping("/async")
    public void async() {
        log.info(LOG_FORMAT, "async");
        observationService.async();
    }

    @GetMapping("/rest-api-call")
    public void restApiCall() {
        log.info(LOG_FORMAT, "restApiCall");
        observationService.restApiCallByTemplate();
    }

    @GetMapping("/client-error")
    public void clientError() {
        log.info(LOG_FORMAT, "clientError");
        throw new IllegalArgumentException("클라이언트 에러 발생");
    }

    @GetMapping("/server-error")
    public void serverError() {
        log.info(LOG_FORMAT, "serverError");
        throw new RuntimeException("서버 에러 발생");
    }

}
