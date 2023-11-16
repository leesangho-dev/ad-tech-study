package leesangho.adtechstudy.webflux.usecase;

import leesangho.adtechstudy.domain.board.BoardItem;
import leesangho.adtechstudy.webflux.board.BoardItemReactiveQueryService;
import leesangho.adtechstudy.webflux.dto.BoardDto;
import leesangho.adtechstudy.webflux.dto.BoardDto.FindItemResponse;
import leesangho.adtechstudy.webflux.infra.cache.ReactiveCacheable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class FindBoardItemUseCase {

    private final BoardItemReactiveQueryService boardItemReactiveQueryService;

    public FindBoardItemUseCase(BoardItemReactiveQueryService boardItemReactiveQueryService) {
        this.boardItemReactiveQueryService = boardItemReactiveQueryService;
    }

    @ReactiveCacheable(value = "findBoardItemUseCase", key = "#boardItemId")
    public Mono<FindItemResponse> execute(String boardItemId) {
        log.info("findBoardItemUseCase.execute: {}", Thread.currentThread().getName());
        Mono<BoardItem> boardItemMono =  boardItemReactiveQueryService.findById(boardItemId);
        return boardItemMono.map(boardItem -> {
            log.info("findBoardItemUseCase.execute.mono: {}", Thread.currentThread().getName());
            return BoardDto.FindItemResponse.of(
                boardItem.getId(), boardItem.getTitle(), boardItem.getBody(),
                boardItem.getCreated().getId(), boardItem.getModified().getId());
        }).log();
    }
}
