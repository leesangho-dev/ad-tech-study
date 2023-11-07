package leesangho.adtechstudy.webflux.board;

import javax.inject.Inject;
import javax.inject.Named;
import leesangho.adtechstudy.domain.board.BoardItem;
import reactor.core.publisher.Mono;

@Named
public class BoardItemReactiveQueryService {

    private final BoardItemReactiveRepository boardItemReactiveRepository;

    @Inject
    public BoardItemReactiveQueryService(BoardItemReactiveRepository boardItemReactiveRepository) {
        this.boardItemReactiveRepository = boardItemReactiveRepository;
    }

    public Mono<String> saveItem(BoardItem boardItem) {
        return boardItemReactiveRepository.saveItem(boardItem);
    }
}
