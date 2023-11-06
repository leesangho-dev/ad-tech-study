package leesangho.adtechstudy.domain.board;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.NoSuchElementException;

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
}
