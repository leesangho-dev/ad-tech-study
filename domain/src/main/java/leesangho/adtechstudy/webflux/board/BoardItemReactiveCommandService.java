package leesangho.adtechstudy.webflux.board;

import javax.inject.Inject;
import javax.inject.Named;
import leesangho.adtechstudy.domain.board.BoardItem;
import reactor.core.publisher.Mono;

@Named
public class BoardItemReactiveCommandService {

    private final BoardItemReactiveRepository boardItemReactiveRepository;

    @Inject
    public BoardItemReactiveCommandService(BoardItemReactiveRepository boardItemReactiveRepository) {
        this.boardItemReactiveRepository = boardItemReactiveRepository;
    }

    public Mono<String> saveItem(BoardItem boardItem) {
        return boardItemReactiveRepository.saveItem(boardItem);
    }

    public Mono<Void> delete(BoardItem boardItem) {
        return boardItemReactiveRepository.delete(boardItem);
    }

    public Mono<String> updateItem(BoardItem boardItem) {
        return boardItemReactiveRepository.updateItem(boardItem);
    }
}
