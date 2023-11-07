package leesangho.adtechstudy.webflux.usecase;

import java.util.function.Function;
import leesangho.adtechstudy.domain.board.BoardItem;
import leesangho.adtechstudy.webflux.board.BoardItemReactiveCommandService;
import leesangho.adtechstudy.webflux.board.BoardItemReactiveQueryService;
import leesangho.adtechstudy.webflux.dto.BoardDto.ItemIdResponse;
import leesangho.adtechstudy.webflux.dto.BoardDto.UpdateItemRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateBoardItemUseCase {

    private final BoardItemReactiveQueryService boardItemReactiveQueryService;

    private final BoardItemReactiveCommandService boardItemReactiveCommandService;

    public UpdateBoardItemUseCase(BoardItemReactiveQueryService boardItemReactiveQueryService,
                                  BoardItemReactiveCommandService boardItemReactiveCommandService) {
        this.boardItemReactiveQueryService = boardItemReactiveQueryService;
        this.boardItemReactiveCommandService = boardItemReactiveCommandService;
    }

    public Mono<ItemIdResponse> execute(String boardItemId, UpdateItemRequest updateItemRequest) {
        return boardItemReactiveQueryService.findById(boardItemId)
                .flatMap(mappedUpdateBoardItem(updateItemRequest))
                .flatMap(boardItemReactiveCommandService::updateItem)
                .map(ItemIdResponse::of);
    }

    private Function<BoardItem, Mono<BoardItem>> mappedUpdateBoardItem(UpdateItemRequest updateItemRequest) {
        return boardItem -> Mono.just(BoardItem.builder()
                .id(boardItem.getId())
                .title(updateItemRequest.getTitle())
                .body(updateItemRequest.getBody())
                .created(boardItem.getCreated().getId())
                .modified(updateItemRequest.getWriter())
                .build());
    }
}
