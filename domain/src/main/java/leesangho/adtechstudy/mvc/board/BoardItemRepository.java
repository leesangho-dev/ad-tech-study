package leesangho.adtechstudy.mvc.board;

import java.util.Optional;
import leesangho.adtechstudy.domain.board.BoardItem;

public interface BoardItemRepository {

    String saveItem(BoardItem boardItem);

    Optional<BoardItem> findById(String boardItemId);

    void delete(BoardItem boardItem);

    void updateItem(BoardItem boardItem);

    <T extends Iterable<BoardItem>> T findAllByOffsetAndLimit(long offset, int limit);
}
