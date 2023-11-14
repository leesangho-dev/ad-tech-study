package leesangho.adtechstudy.webflux.usecase;

import leesangho.adtechstudy.domain.board.BoardItem;
import leesangho.adtechstudy.webflux.board.BoardItemReactiveQueryService;
import leesangho.adtechstudy.webflux.dto.BoardDto;
import leesangho.adtechstudy.webflux.dto.BoardDto.FindItemResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class FindBoardItemUseCase {

    private final BoardItemReactiveQueryService boardItemReactiveQueryService;

    public FindBoardItemUseCase(BoardItemReactiveQueryService boardItemReactiveQueryService) {
        this.boardItemReactiveQueryService = boardItemReactiveQueryService;
    }

    @Cacheable(value = "findBoardItemUseCase", key = "#boardItemId")
    public Mono<FindItemResponse> execute(String boardItemId) {
        Mono<BoardItem> boardItemMono =  boardItemReactiveQueryService.findById(boardItemId);
        return boardItemMono.map(boardItem -> BoardDto.FindItemResponse.of(
                boardItem.getId(), boardItem.getTitle(), boardItem.getBody(),
                boardItem.getCreated().getId(), boardItem.getModified().getId()
        ));

    }
}
