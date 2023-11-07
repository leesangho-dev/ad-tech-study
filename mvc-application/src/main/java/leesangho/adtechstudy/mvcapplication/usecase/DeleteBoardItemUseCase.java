package leesangho.adtechstudy.mvcapplication.usecase;

import leesangho.adtechstudy.domain.board.BoardItem;
import leesangho.adtechstudy.domain.board.BoardItemCommandService;
import leesangho.adtechstudy.domain.board.BoardItemQueryService;
import org.springframework.stereotype.Service;

@Service
public class DeleteBoardItemUseCase {

    private final BoardItemQueryService boardItemQueryService;

    private final BoardItemCommandService boardItemCommandService;

    public DeleteBoardItemUseCase(BoardItemQueryService boardItemQueryService, BoardItemCommandService boardItemCommandService) {
        this.boardItemQueryService = boardItemQueryService;
        this.boardItemCommandService = boardItemCommandService;
    }

    public void execute(String boardItemId) {
        BoardItem boardItem = boardItemQueryService.findById(boardItemId);
        boardItemCommandService.deleteItem(boardItem);
    }
}
