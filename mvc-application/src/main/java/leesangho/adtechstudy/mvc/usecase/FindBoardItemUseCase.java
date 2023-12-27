package leesangho.adtechstudy.mvc.usecase;

import leesangho.adtechstudy.domain.board.BoardItem;
import leesangho.adtechstudy.mvc.board.BoardItemQueryService;
import leesangho.adtechstudy.mvc.dto.BoardDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class FindBoardItemUseCase {

  private final BoardItemQueryService boardItemQueryService;

  public FindBoardItemUseCase(BoardItemQueryService boardItemQueryService) {
    this.boardItemQueryService = boardItemQueryService;
  }

  @Cacheable(value = "findBoardItemUseCase", key = "#boardItemId")
  public BoardDto.FindItemResponse execute(String boardItemId) {
    BoardItem boardItem = boardItemQueryService.findById(boardItemId);
    return BoardDto.FindItemResponse.of(
        boardItem.getId(),
        boardItem.getTitle(),
        boardItem.getBody(),
        boardItem.getCreated().getId(),
        boardItem.getModified().getId()
    );
  }
}
