package leesangho.adtechstudy.mvcapplication.usecase;

import leesangho.adtechstudy.domain.board.BoardItem;
import leesangho.adtechstudy.domain.board.BoardItemQueryService;
import leesangho.adtechstudy.mvcapplication.dto.BoardDto;
import org.springframework.stereotype.Service;

@Service
public class FindBoardItemUseCase {

    private final BoardItemQueryService boardItemQueryService;

    public FindBoardItemUseCase(BoardItemQueryService boardItemQueryService) {
        this.boardItemQueryService = boardItemQueryService;
    }

    public BoardDto.FindItemResponse execute(String boardItemId) {
        BoardItem boardItem = boardItemQueryService.findById(boardItemId);
        return new BoardDto.FindItemResponse(
                boardItem.getId(),
                boardItem.getTitle(),
                boardItem.getBody(),
                boardItem.getCreated().getId(),
                boardItem.getModified().getId()
        );
    }
}
