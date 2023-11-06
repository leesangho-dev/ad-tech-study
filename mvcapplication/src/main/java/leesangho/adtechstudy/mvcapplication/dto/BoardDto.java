package leesangho.adtechstudy.mvcapplication.dto;

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

        @Schema(description = "제목", maxLength = 100)
        private String title;

        @Schema(description = "본문", maxLength = 4000)
        private String body;

        @Schema(description = "작성자 아이디", maxLength = 100)
        private String writer;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "게시글 등록 응답")
    public static class SaveItemResponse {

        @Schema(description = "게시글 아이디", format = "uuid")
        private String id;
    }

}
