package leesangho.adtechstudy.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"leesangho.adtechstudy.domain", "leesangho.adtechstudy.webflux"})
public class StudyWebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyWebfluxApplication.class, args);
    }
}
