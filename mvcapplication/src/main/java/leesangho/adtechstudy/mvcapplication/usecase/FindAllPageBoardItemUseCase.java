package leesangho.adtechstudy.mvcapplication.usecase;

import leesangho.adtechstudy.domain.board.BoardItem;
import leesangho.adtechstudy.domain.board.BoardItemQueryService;
import leesangho.adtechstudy.mvcapplication.dto.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FindAllPageBoardItemUseCase {

    private final BoardItemQueryService boardItemQueryService;

    public FindAllPageBoardItemUseCase(BoardItemQueryService boardItemQueryService) {
        this.boardItemQueryService = boardItemQueryService;
    }

    public Page<BoardDto.FindItemResponse> execute(Pageable pageable) {
        Page<BoardItem> boardItemList = boardItemQueryService.findAllByOffsetAndLimit(pageable.getOffset(), pageable.getPageNumber());
        return boardItemList.map(this::mappedResponse);
    }

    private BoardDto.FindItemResponse mappedResponse(BoardItem boardItem) {
        return BoardDto.FindItemResponse.of(boardItem.getId(),
                boardItem.getTitle(), boardItem.getBody(),
                boardItem.getCreated().getId(), boardItem.getModified().getId());
    }
}
