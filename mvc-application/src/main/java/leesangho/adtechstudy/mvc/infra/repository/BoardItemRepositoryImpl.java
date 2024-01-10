package leesangho.adtechstudy.mvc.infra.repository;

import java.util.Optional;
import leesangho.adtechstudy.domain.board.BoardItem;
import leesangho.adtechstudy.mvc.board.BoardItemRepository;
import leesangho.adtechstudy.mvc.infra.entity.BoardItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class BoardItemRepositoryImpl implements BoardItemRepository {

    private final BoardItemJpaRepository boardItemJpaRepository;

    public BoardItemRepositoryImpl(BoardItemJpaRepository boardItemJpaRepository) {
        this.boardItemJpaRepository = boardItemJpaRepository;
    }

    @Transactional
    @Override
    public String saveItem(BoardItem boardItem) {
        BoardItemEntity boardItemEntity = mappedBoardItemEntity(boardItem);
        boardItemJpaRepository.save(boardItemEntity);
        return boardItem.getId();
    }

    private BoardItemEntity mappedBoardItemEntity(BoardItem boardItem) {
        return BoardItemEntity.builder()
            .id(boardItem.getId())
            .title(boardItem.getTitle())
            .body(boardItem.getBody())
            .created(boardItem.getCreated().getId())
            .modified(boardItem.getModified().getId())
            .build();
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

    @Transactional
    @Override
    public void updateItem(BoardItem boardItem) {
        BoardItemEntity boardItemEntity = mappedBoardItemEntity(boardItem);
        boardItemJpaRepository.save(boardItemEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<BoardItem> findAllByOffsetAndLimit(long offset, int limit) {
        return boardItemJpaRepository.findAll(PageRequest.of((int) offset, limit))
            .map(this::fromDomain);
    }

    private BoardItem fromDomain(BoardItemEntity boardItemEntity) {
        return BoardItem.builder()
            .id(boardItemEntity.getId())
            .title(boardItemEntity.getTitle())
            .body(boardItemEntity.getBody())
            .created(boardItemEntity.getCreated())
            .modified(boardItemEntity.getModified())
            .build();
    }

}
