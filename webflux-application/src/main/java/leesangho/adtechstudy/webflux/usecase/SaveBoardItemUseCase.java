package leesangho.adtechstudy.webflux.usecase;

import leesangho.adtechstudy.domain.board.BoardItem;
import leesangho.adtechstudy.webflux.board.BoardItemReactiveQueryService;
import leesangho.adtechstudy.webflux.dto.BoardDto.ItemIdResponse;
import leesangho.adtechstudy.webflux.dto.BoardDto.SaveItemRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SaveBoardItemUseCase {

    private final BoardItemReactiveQueryService boardItemReactiveQueryService;

    public SaveBoardItemUseCase(BoardItemReactiveQueryService boardItemReactiveQueryService) {
        this.boardItemReactiveQueryService = boardItemReactiveQueryService;
    }

    public Mono<ItemIdResponse> execute(SaveItemRequest saveItemRequest) {
        BoardItem boardItem = BoardItem.writeOf(saveItemRequest.getTitle(), saveItemRequest.getBody(),
                saveItemRequest.getWriter());
        Mono<String> boadItemIdMono = boardItemReactiveQueryService.saveItem(boardItem);
        return boadItemIdMono.map(ItemIdResponse::of);
    }
}
