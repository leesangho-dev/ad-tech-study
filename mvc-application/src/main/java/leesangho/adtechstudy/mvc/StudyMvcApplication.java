package leesangho.adtechstudy.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"leesangho.adtechstudy.domain", "leesangho.adtechstudy.mvc"})
public class StudyMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyMvcApplication.class, args);
    }
}
