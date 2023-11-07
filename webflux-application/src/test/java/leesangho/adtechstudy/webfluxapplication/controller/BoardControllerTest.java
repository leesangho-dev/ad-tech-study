package leesangho.adtechstudy.webfluxapplication.controller;

import leesangho.adtechstudy.domain.board.BoardItem;
import leesangho.adtechstudy.objectmother.BoardItemFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

class BoardControllerTest {

    WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(new BoardController())
                .build();
    }

    @DisplayName("게시글 등록 테스트")
    @Nested
    class SaveBoardItem {

        @DisplayName("성공 테스트")
        @Test
        void saveBoardItem_happy_case() {
            // Given
            BoardItem boardItem = BoardItemFixture.makeBoardItem();
            SaveItemSampleRequest saveItemSampleRequest = new SaveItemSampleRequest(boardItem.getTitle(), boardItem.getBody(), boardItem.getCreated().getId());

            // When & Then
            webTestClient.post()
                    .uri("/v1/board/item")
                    .contentType(MediaType.APPLICATION_JSON)
                    .acceptCharset(StandardCharsets.UTF_8)
                    .body(Mono.just(saveItemSampleRequest), SaveItemSampleRequest.class)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectBody()
                    .jsonPath("$.code").isEqualTo(HttpStatus.CREATED.value())
            ;
        }


        class SaveItemSampleRequest {

            private String title;

            private String body;

            private String writer;

            public SaveItemSampleRequest(String title, String body, String writer) {
                this.title = title;
                this.body = body;
                this.writer = writer;
            }

            public String getTitle() {
                return title;
            }

            public String getBody() {
                return body;
            }

            public String getWriter() {
                return writer;
            }
        }
    }
}
