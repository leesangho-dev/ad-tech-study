package leesangho.adtechstudy.domain.board;

import leesangho.adtechstudy.domain.id.GUIDGenerator;
import leesangho.adtechstudy.domain.member.MemberId;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
public class BoardItem {

    private static final int MAX_TITLE_LENGTH = 100;
    private static final int MAX_BODY_LENGTH = 4000;

    private final String id;

    private final String title;

    private final String body;

    private final MemberId created;

    private final MemberId modified;

    @Builder
    protected BoardItem(String id, String title, String body, String created, String modified) {
        this.id = id;
        validate(title, body);
        this.title = title;
        this.body = body;
        this.created = MemberId.of(created);
        this.modified = MemberId.of(modified);
    }

    private void validate(String title, String body) {
        validateTitle(title);
        validateBody(body);
    }

    private void validateTitle(String title) {
        if (Objects.isNull(title) || title.isEmpty()) {
            throw new IllegalArgumentException("제목이 없습니다.");
        }

        if (title.length() > MAX_TITLE_LENGTH) {
            throw new IllegalArgumentException("제목이 100자를 초과하였습니다.");
        }
    }

    private void validateBody(String body) {
        if (Objects.nonNull(body) && body.length() > MAX_BODY_LENGTH) {
            throw new IllegalArgumentException("본문이 4000자를 초과하였습니다.");
        }
    }

    public static BoardItem writeOf(String title, String body, String writer) {
        return new BoardItem(GUIDGenerator.newId(), title, body, writer, writer);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardItem boardItem = (BoardItem) o;
        return Objects.equals(id, boardItem.id) && Objects.equals(title, boardItem.title) && Objects.equals(body, boardItem.body) && Objects.equals(created, boardItem.created) && Objects.equals(modified, boardItem.modified);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, body, created, modified);
    }

    @Override
    public String toString() {
        return "BoardItem{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", created=" + created +
                ", modified=" + modified +
                '}';
    }
}
