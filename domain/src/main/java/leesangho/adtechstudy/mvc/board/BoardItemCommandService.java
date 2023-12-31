package leesangho.adtechstudy.mvc.board;

import javax.inject.Inject;
import javax.inject.Named;
import leesangho.adtechstudy.domain.board.BoardItem;

@Named
public class BoardItemCommandService {

    private final BoardItemRepository boardItemRepository;

    @Inject
    public BoardItemCommandService(BoardItemRepository boardItemRepository) {
        this.boardItemRepository = boardItemRepository;
    }

    public String saveItem(BoardItem boardItem) {
        return boardItemRepository.saveItem(boardItem);
    }

    public void deleteItem(BoardItem boardItem) {
        boardItemRepository.delete(boardItem);
    }

    public void updateItem(BoardItem boardItem) {
        boardItemRepository.updateItem(boardItem);
    }
}
