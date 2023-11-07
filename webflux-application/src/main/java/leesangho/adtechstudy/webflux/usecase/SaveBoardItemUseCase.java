package leesangho.adtechstudy.webflux.usecase;

import leesangho.adtechstudy.domain.board.BoardItem;
import leesangho.adtechstudy.webflux.board.BoardItemReactiveCommandService;
import leesangho.adtechstudy.webflux.dto.BoardDto.ItemIdResponse;
import leesangho.adtechstudy.webflux.dto.BoardDto.SaveItemRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SaveBoardItemUseCase {

    private final BoardItemReactiveCommandService boardItemReactiveCommandService;

    public SaveBoardItemUseCase(BoardItemReactiveCommandService boardItemReactiveCommandService) {
        this.boardItemReactiveCommandService = boardItemReactiveCommandService;
    }

    public Mono<ItemIdResponse> execute(SaveItemRequest saveItemRequest) {
        BoardItem boardItem = BoardItem.writeOf(saveItemRequest.getTitle(), saveItemRequest.getBody(),
                saveItemRequest.getWriter());
        Mono<String> boadItemIdMono = boardItemReactiveCommandService.saveItem(boardItem);
        return boadItemIdMono.map(ItemIdResponse::of);
    }
}
