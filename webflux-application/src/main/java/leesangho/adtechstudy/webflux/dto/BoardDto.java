package leesangho.adtechstudy.webflux.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class BoardDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SaveItemRequest {

        private String title;

        private String body;

        private String writer;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    public static class ItemIdResponse {

        private String id;
    }
}
