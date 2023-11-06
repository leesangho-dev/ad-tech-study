package leesangho.adtechstudy.domain.member;

import lombok.Getter;

import java.util.Objects;

@Getter
public class MemberId {

    private static final int MAX_MEMBER_ID_LENGTH = 100;

    private final String id;

    protected MemberId(String id) {
        validateId(id);
        this.id = id;
    }

    private void validateId(String id) {
        if (Objects.isNull(id) || id.isEmpty()) {
            throw new IllegalArgumentException("사용자 아이디가 없습니다.");
        }

        if (id.length() > MAX_MEMBER_ID_LENGTH) {
            throw new IllegalArgumentException("사용자 아이디가 100자를 초과하였습니다.");
        }
    }

    public static MemberId of(String id) {
        return new MemberId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberId memberId = (MemberId) o;
        return Objects.equals(id, memberId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "MemberId{" +
                "id='" + id + '\'' +
                '}';
    }
}
