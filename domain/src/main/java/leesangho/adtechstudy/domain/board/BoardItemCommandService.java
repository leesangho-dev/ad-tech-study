package leesangho.adtechstudy.domain.board;

import javax.inject.Inject;
import javax.inject.Named;

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
}
