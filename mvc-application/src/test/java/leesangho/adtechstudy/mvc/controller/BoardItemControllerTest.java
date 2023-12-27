package leesangho.adtechstudy.mvc.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import leesangho.adtechstudy.domain.board.BoardItem;
import leesangho.adtechstudy.domain.id.GUIDGenerator;
import leesangho.adtechstudy.mvc.dto.BoardDto;
import leesangho.adtechstudy.mvc.usecase.DeleteBoardItemUseCase;
import leesangho.adtechstudy.mvc.usecase.FindAllPageBoardItemUseCase;
import leesangho.adtechstudy.mvc.usecase.FindBoardItemUseCase;
import leesangho.adtechstudy.mvc.usecase.SaveBoardItemUseCase;
import leesangho.adtechstudy.mvc.usecase.UpdateBoardItemUseCase;
import leesangho.adtechstudy.objectmother.BoardItemFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@DisplayName("게시글 Presentation 테스트")
@MockitoSettings(strictness = Strictness.LENIENT)
class BoardItemControllerTest {

    MockMvc mockMvc;

    ObjectMapper objectMapper;

    @Mock
    SaveBoardItemUseCase saveBoardItemUseCase;

    @Mock
    FindBoardItemUseCase findBoardItemUseCase;

    @Mock
    DeleteBoardItemUseCase deleteBoardItemUseCase;

    @Mock
    UpdateBoardItemUseCase updateBoardItemUseCase;

    @Mock
    FindAllPageBoardItemUseCase findAllPageBoardItemUseCase;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new BoardController(saveBoardItemUseCase, findBoardItemUseCase, deleteBoardItemUseCase, updateBoardItemUseCase, findAllPageBoardItemUseCase))
                .setControllerAdvice(new DefaultControllerAdvice())
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
        objectMapper = Jackson2ObjectMapperBuilder.json()
                .build();
    }

    @DisplayName("게시글 저장 요청/응답 테스트")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @Nested
    class SaveBoardItem {

        String boardItemId = GUIDGenerator.newId();

        @DisplayName("성공 케이스")
        @Test
        void saveBoardItem_happy_case() throws Exception {
            // Given
            SaveBoardSampleRequest saveBoardSampleRequest = new SaveBoardSampleRequest("제목", "본문", "작성자");
            String body = objectMapper.writeValueAsString(saveBoardSampleRequest);
            given(saveBoardItemUseCase.execute(any()))
                    .willReturn(BoardDto.ItemIdResponse.of(boardItemId));

            // When & Then
            mockMvc.perform(post("/v1/board/item")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8)
                            .content(body)
                    )
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.code").value(HttpStatus.CREATED.value()))
                    .andExpect(jsonPath("$.message").value("게시글 생성이 완료 되었습니다."))
                    .andExpect(jsonPath("$.data").isNotEmpty())
                    .andExpect(jsonPath("$.data.id").value(boardItemId))
            ;
        }

        class SaveBoardSampleRequest {

            private final String title;

            private final String body;

            private final String writer;

            public SaveBoardSampleRequest(String title, String body, String writer) {
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

        @DisplayName("실패 케이스 - 필수 값을 누락하고 요청을 할 경우")
        @ParameterizedTest
        @MethodSource("bad_case_wrong_request")
        void saveBoardItem_bad_case_wrong_request(String body, String errorMessage) throws Exception {
            // Given
            given(saveBoardItemUseCase.execute(any()))
                    .willThrow(new IllegalArgumentException(errorMessage));

            // When & Then
            mockMvc.perform(post("/v1/board/item")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8)
                            .content(body)
                    )
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
                    .andExpect(jsonPath("$.message").value(errorMessage))
                    .andExpect(jsonPath("$.data").isEmpty())
            ;
        }

        Stream<Arguments> bad_case_wrong_request() {
            return Stream.of(
                Arguments.of("{\"item\":\"제목\",\"image\":\"본문\",\"writer\":\"작성자\"}", "제목이 없습니다."),
                Arguments.of("{\"title\":\"제목\",\"image\":\"본문\",\"writer\":\"\"}", "사용자 아이디가 잘못 되었습니다.")
            );
        }
    }

    @DisplayName("게시글 조회 테스트")
    @Nested
    class FindBoardItem {

        @DisplayName("성공 케이스")
        @Test
        void findBoardItem_happy_case() throws Exception {
            // Given
            BoardItem boardItem = BoardItemFixture.fixtureBoardItem();
            String boardItemId = boardItem.getId();
            given(findBoardItemUseCase.execute(boardItemId))
                    .willReturn(BoardDto.FindItemResponse.of(boardItemId, boardItem.getTitle(), boardItem.getBody(), boardItem.getCreated().getId(), boardItem.getModified().getId()));

            // When & Then
            mockMvc.perform(get("/v1/board/item/{id}", boardItemId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8)
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
                    .andExpect(jsonPath("$.data").isNotEmpty())
            ;
        }

        @DisplayName("실패 케이스 - 게시글 존재 하지 않음")
        @Test
        void findBoardItem_bad_case_not_found() throws Exception {
            // Given
            String boardItemId = GUIDGenerator.newId();
            given(findBoardItemUseCase.execute(boardItemId))
                    .willThrow(new NoSuchElementException("게시글을 찾을 수 없습니다."));

            // When & then
            mockMvc.perform(get("/v1/board/item/{id}", boardItemId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8)
                    )
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
            ;
        }
    }

    @DisplayName("게시글 삭제 테스트")
    @Nested
    class DeleteBoardItem {

        String boardItemId = GUIDGenerator.newId();

        @DisplayName("성공 케이스")
        @Test
        void deleteBoardItem_happy_case() throws Exception {
            // Given
            doNothing()
                    .when(deleteBoardItemUseCase)
                            .execute(boardItemId);

            // When & then
            mockMvc.perform(delete("/v1/board/item/{id}", boardItemId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8)
                    )
                    .andDo(print())
                    .andExpect(status().isNoContent())
                    .andExpect(jsonPath("$.code").value(HttpStatus.NO_CONTENT.value()))
            ;
        }

        @DisplayName("실패 케이스 - 게시글을 찾지 못함")
        @Test
        void deleteBoardItem_bad_case_not_found() throws Exception {
            // Given
            doThrow(new NoSuchElementException("게시글을 찾지 못하였습니다."))
                    .when(deleteBoardItemUseCase)
                            .execute(boardItemId);

            // When & then
            mockMvc.perform(delete("/v1/board/item/{id}", boardItemId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8)
                    )
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
            ;
        }
    }

    @DisplayName("게시글 수정 테스트")
    @Nested
    class UpdateBoardItem {

        String boardItemId = GUIDGenerator.newId();

        @DisplayName("성공 케이스")
        @Test
        void updateBoardItem_happy_case() throws Exception {
            // Given
            UpdateBoardSampleRequest updateBoardSampleRequest = new UpdateBoardSampleRequest("제목", "본문", "작성자");
            String body = objectMapper.writeValueAsString(updateBoardSampleRequest);
            given(updateBoardItemUseCase.execute(any(), any()))
                    .willReturn(BoardDto.ItemIdResponse.of(boardItemId));

            // When & Then
            mockMvc.perform(put("/v1/board/item/{id}", boardItemId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8)
                            .content(body)
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
            ;
        }

        class UpdateBoardSampleRequest {

            private String title;

            private String body;

            private String writer;

            public UpdateBoardSampleRequest(String title, String body, String writer) {
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

        @DisplayName("실패 케이스 - 게시글을 찾지 못함")
        @Test
        void updateBoardItem_bad_case_not_found() throws Exception {
            // Given
            UpdateBoardSampleRequest updateBoardSampleRequest = new UpdateBoardSampleRequest("제목", "본문", "작성자");
            String body = objectMapper.writeValueAsString(updateBoardSampleRequest);
            given(updateBoardItemUseCase.execute(any(), any()))
                    .willThrow(new NoSuchElementException("게시글을 찾지 못하였습니다."));

            // When & Then
            mockMvc.perform(put("/v1/board/item/{id}", boardItemId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8)
                            .content(body)
                    )
                    .andDo(print())
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.code").value(HttpStatus.NOT_FOUND.value()))
            ;
        }

        @DisplayName("실패 케이스 - 게시글을 찾지 못함")
        @Test
        void updateBoardItem_bad_case_bad_request() throws Exception {
            // Given
            UpdateBoardSampleRequest updateBoardSampleRequest = new UpdateBoardSampleRequest("", "본문", "작성자");
            String body = objectMapper.writeValueAsString(updateBoardSampleRequest);
            given(updateBoardItemUseCase.execute(any(), any()))
                    .willThrow(new IllegalArgumentException("제목이 없습니다."));

            // When & Then
            mockMvc.perform(put("/v1/board/item/{id}", boardItemId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8)
                            .content(body)
                    )
                    .andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
            ;
        }
    }

    @DisplayName("게시글 목록 조회")
    @Nested
    class FindAllPageBoardItems {

        @DisplayName("성공 케이스")
        @Test
        void findAllPageBoardItems_happy_case() throws Exception {
            // Given
            List<BoardDto.FindItemResponse> itemResponseList = BoardItemFixture.fixtureBoardItems(10)
                    .stream()
                    .map(this::mappedBoardItemResponse)
                    .collect(Collectors.toList());

            given(findAllPageBoardItemUseCase.execute(PageRequest.of(0, 10)))
                    .willReturn(new PageImpl<>(itemResponseList, PageRequest.of(0, 10), 30));

            // When & Then
            mockMvc.perform(get("/v1/board/item")
                            .param("page", "0")
                            .param("size", "10")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8)
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
            ;
        }

        private BoardDto.FindItemResponse mappedBoardItemResponse(BoardItem boardItem) {
            return BoardDto.FindItemResponse.of(
                    boardItem.getId(), boardItem.getTitle(), boardItem.getBody(),
                    boardItem.getCreated().getId(), boardItem.getModified().getId()
            );
        }
    }
}
