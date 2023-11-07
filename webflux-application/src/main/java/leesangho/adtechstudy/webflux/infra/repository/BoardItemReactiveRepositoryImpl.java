package leesangho.adtechstudy.webflux.infra.repository;

import java.util.NoSuchElementException;
import leesangho.adtechstudy.domain.board.BoardItem;
import leesangho.adtechstudy.webflux.board.BoardItemReactiveRepository;
import leesangho.adtechstudy.webflux.infra.document.BoardItemDocument;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class BoardItemReactiveRepositoryImpl implements BoardItemReactiveRepository {

    private final BoardItemReactiveMongoRepository boardItemReactiveMongoRepository;

    public BoardItemReactiveRepositoryImpl(BoardItemReactiveMongoRepository boardItemReactiveMongoRepository) {
        this.boardItemReactiveMongoRepository = boardItemReactiveMongoRepository;
    }

    @Override
    public Mono<String> saveItem(BoardItem boardItem) {
        BoardItemDocument boardItemDocument = mappedDocument(boardItem);
        return boardItemReactiveMongoRepository.save(boardItemDocument)
                .map(BoardItemDocument::idString);
    }

    private BoardItemDocument mappedDocument(BoardItem boardItem) {
        return BoardItemDocument.builder()
                .title(boardItem.getTitle())
                .body(boardItem.getBody())
                .created(boardItem.getCreated().getId())
                .modified(boardItem.getModified().getId())
                .build();
    }

    @Override
    public Mono<BoardItem> findById(String boardItemId) {
        return boardItemReactiveMongoRepository.findById(new ObjectId(boardItemId))
                .map(this::fromDomain)
                .switchIfEmpty(Mono.error(new NoSuchElementException("게시글을 찾지 못하였습니다.")));
    }

    private BoardItem fromDomain(BoardItemDocument boardItemDocument) {
        return BoardItem.builder()
                .id(boardItemDocument.idString())
                .title(boardItemDocument.getTitle())
                .body(boardItemDocument.getBody())
                .created(boardItemDocument.getCreated())
                .modified(boardItemDocument.getModified())
                .build();
    }

}
