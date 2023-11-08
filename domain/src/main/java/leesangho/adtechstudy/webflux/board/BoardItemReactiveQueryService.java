package leesangho.adtechstudy.webflux.board;

import leesangho.adtechstudy.domain.board.BoardItem;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class BoardItemReactiveQueryService {

    private final BoardItemReactiveRepository boardItemReactiveRepository;

    @Inject
    public BoardItemReactiveQueryService(BoardItemReactiveRepository boardItemReactiveRepository) {
        this.boardItemReactiveRepository = boardItemReactiveRepository;
    }

    public Mono<BoardItem> findById(String boardItemId) {
        return boardItemReactiveRepository.findById(boardItemId);
    }

    public Flux<BoardItem> findAllByOffsetAndLimit(long offset, int pageSize) {
        return boardItemReactiveRepository.findAllByOffsetAndLimit(offset, pageSize);
    }
}
