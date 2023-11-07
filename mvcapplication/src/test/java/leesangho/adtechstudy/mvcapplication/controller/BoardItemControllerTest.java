package leesangho.adtechstudy.mvcapplication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import leesangho.adtechstudy.domain.id.GUIDGenerator;
import leesangho.adtechstudy.mvcapplication.dto.BoardDto;
import leesangho.adtechstudy.mvcapplication.usecase.DeleteBoardItemUseCase;
import leesangho.adtechstudy.mvcapplication.usecase.FindBoardItemUseCase;
import leesangho.adtechstudy.mvcapplication.usecase.SaveBoardItemUseCase;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new BoardController(saveBoardItemUseCase, findBoardItemUseCase, deleteBoardItemUseCase))
                .setControllerAdvice(new DefaultControllerAdvice())
                .defaultResponseCharacterEncoding(StandardCharsets.UTF_8)
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
                    .willReturn(BoardDto.SaveItemResponse.of(boardItemId));

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

        String boardItemId = GUIDGenerator.newId();

        @DisplayName("성공 케이스")
        @Test
        void findBoardItem_happy_case() throws Exception {
            // Given
            given(findBoardItemUseCase.execute(boardItemId))
                    .willReturn(BoardDto.FindItemResponse.of(boardItemId, "제목", "본문", "작성자" ,"작성자"));

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

        @DisplayName("실패 케이스 - 게시글 아이디 입력 안함")
        @Test
        void findBoardItem_bad_case_bad_request() throws Exception {
            // Given

            // When & Then
            mockMvc.perform(get("/v1/board/item/{id}", "")
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8)
                    )
                    .andDo(print())
                    .andExpect(status().isBadRequest())
            ;
        }

        @DisplayName("실패 케이스 - 게시글 존재 하지 않음")
        @Test
        void findBoardItem_bad_case_not_found() throws Exception {
            // Given
            given(findBoardItemUseCase.execute(boardItemId))
                    .willThrow(new NoSuchElementException("게시글을 찾을 수 없습니다."));

            // When & then
            mockMvc.perform(get("/v1/board/item/{id}", boardItemId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding(StandardCharsets.UTF_8)
                    )
                    .andDo(print())
                    .andExpect(status().isNotFound())
            ;
        }
    }

    @DisplayName("게시글 삭제")
    @Nested
    class DeleteBoardItem {

        String boardItemId = GUIDGenerator.newId();

        @DisplayName("성공 케이스")
        @Test
        void deleteBoardItem_happy_case() throws Exception {
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
            ;
        }

        @DisplayName("실패 케이스 - 게시글을 찾지 못함")
        @Test
        void deleteBoardItem_bad_case_not_found() throws Exception {
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
            ;
        }
    }
}
