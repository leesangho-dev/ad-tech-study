package leesangho.adtechstudy.webflux.board;

import leesangho.adtechstudy.domain.board.BoardItem;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BoardItemReactiveRepository {

    Mono<String> saveItem(BoardItem boardItem);

    Mono<BoardItem> findById(String boardItemId);

    Mono<Void> delete(BoardItem boardItem);

    Mono<String> updateItem(BoardItem boardItem);

    Flux<BoardItem> findAllByOffsetAndLimit(long offset, int pageSize);
}
