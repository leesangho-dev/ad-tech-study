package leesangho.adtechstudy.webflux.usecase;

import leesangho.adtechstudy.domain.board.BoardItem;
import leesangho.adtechstudy.webflux.board.BoardItemReactiveQueryService;
import leesangho.adtechstudy.webflux.dto.BoardDto;
import leesangho.adtechstudy.webflux.dto.BoardDto.FindItemResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class FindAllPageBoardItemsUseCase {

    private final BoardItemReactiveQueryService boardItemReactiveQueryService;

    public FindAllPageBoardItemsUseCase(BoardItemReactiveQueryService boardItemReactiveQueryService) {
        this.boardItemReactiveQueryService = boardItemReactiveQueryService;
    }

    public Mono<Page<FindItemResponse>> execute(Pageable pageable) {
        Mono<Page<BoardItem>> pageMono = boardItemReactiveQueryService.findAllByOffsetAndLimit(pageable.getOffset(), pageable.getPageSize());
        return pageMono.map(boardItems -> boardItems.map(this::mappedResponse));
    }

    private BoardDto.FindItemResponse mappedResponse(BoardItem boardItem) {
        return BoardDto.FindItemResponse.of(boardItem.getId(),
                boardItem.getTitle(), boardItem.getBody(),
                boardItem.getCreated().getId(), boardItem.getModified().getId());
    }
}
