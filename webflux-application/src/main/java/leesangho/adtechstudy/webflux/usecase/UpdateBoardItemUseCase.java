package leesangho.adtechstudy.webflux.usecase;

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
      return boardItemReactiveQueryService.findById(boardItemId) // Mono<Board>
          .map(boardItem -> mappedUpdateBoardItem(boardItem, updateItemRequest)) // Mono<Mono<Board>
          .flatMap(boardItemReactiveCommandService::updateItem) // Mono<Board>
                .map(ItemIdResponse::of);
    }

  private BoardItem mappedUpdateBoardItem(BoardItem boardItem,
      UpdateItemRequest updateItemRequest) {
    return BoardItem.builder()
                .id(boardItem.getId())
                .title(updateItemRequest.getTitle())
                .body(updateItemRequest.getBody())
                .created(boardItem.getCreated().getId())
                .modified(updateItemRequest.getWriter())
        .build();
    }
}
