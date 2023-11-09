package leesangho.adtechstudy.domain.board;

import leesangho.adtechstudy.domain.member.MemberId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardItemTest {

    @DisplayName("게시글 생성 테스트")
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class WriteBoardItem {

        @DisplayName("성공 테스트")
        @Test
        void happy_case() {
            // Given
            String title = "제목";
            String body = "본문";
            String writer = "작성자";

            // When
            BoardItem boardItem = BoardItem.writeOf(title, body, writer);

            // Then
            assertThat(boardItem).isNotNull();
            assertThat(boardItem.getTitle()).isEqualTo(title);
            assertThat(boardItem.getBody()).isEqualTo(body);
            MemberId memberId = MemberId.of(writer);
            assertThat(boardItem.getCreated()).isEqualTo(memberId);
            assertThat(boardItem.getModified()).isEqualTo(memberId);
        }

        @DisplayName("필수값 체크 테스트")
        @ParameterizedTest
        @MethodSource("badCaseArguments")
        void bad_case(String title, String body, String writer, String errorMessage) {
            // When & Then
            assertThatThrownBy(() -> BoardItem.writeOf(title, body, writer))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage(errorMessage)
                    ;
        }

        Stream<Arguments> badCaseArguments() {
            return Stream.of(
                    Arguments.of(null, "본문", "작성자", "제목이 없습니다."),
                    Arguments.of("", "본문", "작성자", "제목이 없습니다."),
                    Arguments.of("SS4Xkx9SS4Xkx9SS4Xkx9SS4Xkx9SS4Xkx9SS4Xkx9SS4Xkx9SS4Xkx9SS4Xkx9SS4Xkx9SS4Xkx9SS4Xkx9SS4Xkx9SS4Xkx9SS4Xkx9SS4Xkx9SS4Xkx9SS4Xkx9", "본문", "작성자", "제목이 100자를 초과하였습니다."),
                    Arguments.of("제목", "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", "작성자", "본문이 4000자를 초과하였습니다."),
                    Arguments.of("제목", "본문", "", "사용자 아이디가 없습니다."),
                    Arguments.of("제목", "본문", "SS4Xkx9SS4Xkx9SS4Xkx9SS4Xkx9SS4Xkx9SS4Xkx9SS4Xkx9SS4Xkx9SS4Xkx9SS4Xkx9SS4Xkx9SS4Xkx9SS4Xkx9SS4Xkx9SS4Xkx9SS4Xkx9SS4Xkx9SS4Xkx9", "사용자 아이디가 100자를 초과하였습니다.")
            );
        }

    }

}
