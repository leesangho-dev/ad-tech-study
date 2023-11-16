package leesangho.adtechstudy.webflux.controller;

import leesangho.adtechstudy.domain.board.BoardItem;
import leesangho.adtechstudy.objectmother.BoardItemFixture;
import leesangho.adtechstudy.webflux.dto.BoardDto;
import leesangho.adtechstudy.webflux.usecase.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@MockitoSettings(strictness = Strictness.LENIENT)
class BoardControllerTest {

    WebTestClient webTestClient;

    @Mock
    SaveBoardItemUseCase saveBoardItemUseCase;

    @Mock
    FindBoardItemUseCase findBoardItemUseCase;

    @Mock
    DeleteBoardItemUseCase deleteBoardItemUseCase;

    @Mock
    UpdateBoardItemUseCase updateBoardItemUseCase;

    @Mock
    FindAllPageBoardItemsUseCase findAllPageBoardItemsUseCase;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(new BoardController(saveBoardItemUseCase, findBoardItemUseCase,
                        deleteBoardItemUseCase, updateBoardItemUseCase, findAllPageBoardItemsUseCase))
                .build();
    }

    @DisplayName("게시글 등록 테스트")
    @Nested
    class SaveBoardItem {

        @DisplayName("성공 테스트")
        @Test
        void saveBoardItem_happy_case() {
            // Given
            BoardItem boardItem = BoardItemFixture.fixtureBoardItem();
            SaveItemSampleRequest saveItemSampleRequest = new SaveItemSampleRequest(boardItem.getTitle(), boardItem.getBody(), boardItem.getCreated().getId());
            given(saveBoardItemUseCase.execute(any()))
                    .willReturn(Mono.just(BoardDto.ItemIdResponse.of(boardItem.getId())));

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

    @DisplayName("")
    @Nested
    class FindBoardItem {

    }
}
