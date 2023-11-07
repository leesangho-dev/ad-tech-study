package leesangho.adtechstudy.domain.board;

import java.util.Optional;

public interface BoardItemRepository {

    String saveItem(BoardItem boardItem);

    Optional<BoardItem> findById(String boardItemId);

    void delete(BoardItem boardItem);

    void updateItem(BoardItem boardItem);
}
