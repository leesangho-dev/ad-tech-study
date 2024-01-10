package leesangho.adtechstudy.observation.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;

@SpringBootApplication
public class ObservationWebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(ObservationWebfluxApplication.class, args);
        Hooks.enableAutomaticContextPropagation();
    }
}
