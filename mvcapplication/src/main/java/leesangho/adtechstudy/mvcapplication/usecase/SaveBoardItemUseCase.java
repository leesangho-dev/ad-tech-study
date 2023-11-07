package leesangho.adtechstudy.mvcapplication.usecase;

import leesangho.adtechstudy.domain.board.BoardItemCommandService;
import leesangho.adtechstudy.domain.board.BoardItem;
import leesangho.adtechstudy.mvcapplication.dto.BoardDto;
import org.springframework.stereotype.Service;

@Service
public class SaveBoardItemUseCase {

    private final BoardItemCommandService boardItemCommandService;

    public SaveBoardItemUseCase(BoardItemCommandService boardItemCommandService) {
        this.boardItemCommandService = boardItemCommandService;
    }

    public BoardDto.ItemIdResponse execute(BoardDto.SaveItemRequest saveItemRequest) {
        BoardItem boardItem = BoardItem.writeOf(saveItemRequest.getTitle(), saveItemRequest.getBody(), saveItemRequest.getWriter());
        String boardItemId = boardItemCommandService.saveItem(boardItem);
        return BoardDto.ItemIdResponse.of(boardItemId);
    }
}
