package leesangho.adtechstudy.mvcapplication.infra.repository;

import leesangho.adtechstudy.domain.board.BoardItem;
import leesangho.adtechstudy.domain.board.BoardItemRepository;
import leesangho.adtechstudy.mvcapplication.infra.entity.BoardItemEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class BoardItemRepositoryImpl implements BoardItemRepository {

    private final BoardItemJpaRepository boardItemJpaRepository;

    public BoardItemRepositoryImpl(BoardItemJpaRepository boardItemJpaRepository) {
        this.boardItemJpaRepository = boardItemJpaRepository;
    }

    @Transactional
    @Override
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

    @Transactional(readOnly = true)
    @Override
    public Optional<BoardItem> findById(String boardItemId) {
        return boardItemJpaRepository.findById(boardItemId)
                .map(this::fromDomain);
    }

    @Transactional
    @Override
    public void delete(BoardItem boardItem) {
        boardItemJpaRepository.deleteById(boardItem.getId());
    }

    private BoardItem fromDomain(BoardItemEntity boardItemEntity) {
        return BoardItem.builder()
                .id(boardItemEntity.getId())
                .title(boardItemEntity.getTitle())
                .body(boardItemEntity.getBody())
                .created(boardItemEntity.getId())
                .modified(boardItemEntity.getId())
                .build();
    }

}
