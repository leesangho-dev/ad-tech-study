package leesangho.adtechstudy.webfluxapplication.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class BoardDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SaveRequest {

        private String title;

        private String body;

        private String writer;
    }
}
