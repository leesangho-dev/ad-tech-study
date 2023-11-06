package leesangho.adtechstudy.mvcapplication.infra.repository;

import leesangho.adtechstudy.domain.board.BoardItem;
import leesangho.adtechstudy.domain.board.BoardItemRepository;
import leesangho.adtechstudy.mvcapplication.infra.entity.BoardItemEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class BoardItemRepositoryImpl implements BoardItemRepository {

    private final BoardItemJpaRepository boardItemJpaRepository;

    public BoardItemRepositoryImpl(BoardItemJpaRepository boardItemJpaRepository) {
        this.boardItemJpaRepository = boardItemJpaRepository;
    }

    @Override
    @Transactional
    public String saveItem(BoardItem boardItem) {
        BoardItemEntity boardItemEntity = BoardItemEntity.builder()
                .id(boardItem.getId())
                .title(boardItem.getTitle())
                .body(boardItem.getBody())
                .created(boardItem.getCreated().getId())
                .modified(boardItem.getModified().getId())
                .build();
        boardItemJpaRepository.save(boardItemEntity);
        return boardItem.getId();
    }
}
