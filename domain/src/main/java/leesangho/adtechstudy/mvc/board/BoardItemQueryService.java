package leesangho.adtechstudy.mvc.board;

import java.util.NoSuchElementException;
import javax.inject.Inject;
import javax.inject.Named;
import leesangho.adtechstudy.domain.board.BoardItem;

@Named
public class BoardItemQueryService {

    private final BoardItemRepository boardItemRepository;

    @Inject
    public BoardItemQueryService(BoardItemRepository boardItemRepository) {
        this.boardItemRepository = boardItemRepository;
    }

    public BoardItem findById(String boardItemId) {
        return boardItemRepository.findById(boardItemId)
            .orElseThrow(() -> new NoSuchElementException("게시글을 찾지 못하였습니다."));
    }

    public <T extends Iterable<BoardItem>> T findAllByOffsetAndLimit(long offset, int limit) {
        return boardItemRepository.findAllByOffsetAndLimit(offset, limit);
    }
}
