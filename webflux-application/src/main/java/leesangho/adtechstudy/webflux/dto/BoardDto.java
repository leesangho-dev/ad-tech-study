package leesangho.adtechstudy.webflux.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class BoardDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "게시글 등록 요청")
    public static class SaveItemRequest {

        @Schema(description = "제목")
        private String title;

        @Schema(description = "본문")
        private String body;

        @Schema(description = "작성자")
        private String writer;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor(staticName = "of")
    @Schema(description = "게시글 아이디 응답")
    public static class ItemIdResponse {

        @Schema(description = "게시글 아이디")
        private String id;
    }
}
