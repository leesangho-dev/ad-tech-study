package leesangho.adtechstudy.mvcapplication.usecase;

import leesangho.adtechstudy.domain.board.BoardItem;
import leesangho.adtechstudy.domain.board.BoardItemCommandService;
import leesangho.adtechstudy.domain.board.BoardItemQueryService;
import leesangho.adtechstudy.mvcapplication.dto.BoardDto;
import org.springframework.stereotype.Service;

@Service
public class UpdateBoardItemUseCase {

    private final BoardItemQueryService boardItemQueryService;

    private final BoardItemCommandService boardItemCommandService;

    public UpdateBoardItemUseCase(BoardItemQueryService boardItemQueryService, BoardItemCommandService boardItemCommandService) {
        this.boardItemQueryService = boardItemQueryService;
        this.boardItemCommandService = boardItemCommandService;
    }

    public BoardDto.ItemIdResponse execute(String boardItemId, BoardDto.UpdateItemRequest updateItemRequest) {
        BoardItem boardItem = boardItemQueryService.findById(boardItemId);

        BoardItem updatedBoardItem = BoardItem.builder()
                .id(boardItemId)
                .title(updateItemRequest.getTitle())
                .body(updateItemRequest.getBody())
                .created(boardItem.getCreated().getId())
                .modified(updateItemRequest.getWriter())
                .build();

        boardItemCommandService.updateItem(updatedBoardItem);
        return BoardDto.ItemIdResponse.of(boardItemId);
    }
}
