package leesangho.adtechstudy.webflux.board;

import leesangho.adtechstudy.domain.board.BoardItem;
import reactor.core.publisher.Mono;

public interface BoardItemReactiveRepository {

    Mono<String> saveItem(BoardItem boardItem);

}
