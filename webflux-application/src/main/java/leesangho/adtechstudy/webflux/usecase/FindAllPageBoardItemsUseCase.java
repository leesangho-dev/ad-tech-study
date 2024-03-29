package leesangho.adtechstudy.webflux.usecase;

import leesangho.adtechstudy.domain.board.BoardItem;
import leesangho.adtechstudy.webflux.board.BoardItemReactiveQueryService;
import leesangho.adtechstudy.webflux.dto.BoardDto;
import leesangho.adtechstudy.webflux.dto.BoardDto.FindItemResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class FindAllPageBoardItemsUseCase {

    private final BoardItemReactiveQueryService boardItemReactiveQueryService;

    public FindAllPageBoardItemsUseCase(
        BoardItemReactiveQueryService boardItemReactiveQueryService) {
        this.boardItemReactiveQueryService = boardItemReactiveQueryService;
    }

    public Flux<FindItemResponse> execute(Pageable pageable) {
        return boardItemReactiveQueryService.findAllByOffsetAndLimit(pageable.getOffset(),
                pageable.getPageSize())
            .map(this::mappedResponse);
    }

    private BoardDto.FindItemResponse mappedResponse(BoardItem boardItem) {
        return BoardDto.FindItemResponse.of(boardItem.getId(),
            boardItem.getTitle(), boardItem.getBody(),
            boardItem.getCreated().getId(), boardItem.getModified().getId());
    }
}
