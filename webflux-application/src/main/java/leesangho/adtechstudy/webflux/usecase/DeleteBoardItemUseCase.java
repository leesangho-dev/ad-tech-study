package leesangho.adtechstudy.webflux.usecase;

import leesangho.adtechstudy.webflux.board.BoardItemReactiveCommandService;
import leesangho.adtechstudy.webflux.board.BoardItemReactiveQueryService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DeleteBoardItemUseCase {

    private final BoardItemReactiveQueryService boardItemReactiveQueryService;

    private final BoardItemReactiveCommandService boardItemReactiveCommandService;

    public DeleteBoardItemUseCase(BoardItemReactiveQueryService boardItemReactiveQueryService,
        BoardItemReactiveCommandService boardItemReactiveCommandService) {
        this.boardItemReactiveQueryService = boardItemReactiveQueryService;
        this.boardItemReactiveCommandService = boardItemReactiveCommandService;
    }

    public Mono<Void> execute(String boardItemId) {
        return boardItemReactiveQueryService.findById(boardItemId)
            .flatMap(boardItemReactiveCommandService::delete);
    }
}
