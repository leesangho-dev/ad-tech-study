package leesangho.adtechstudy.webflux.infra.repository;

import leesangho.adtechstudy.domain.board.BoardItem;
import leesangho.adtechstudy.webflux.board.BoardItemReactiveRepository;
import leesangho.adtechstudy.webflux.infra.document.BoardItemDocument;
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
                .body(boardItem.getTitle())
                .created(boardItem.getCreated().getId())
                .modified(boardItem.getModified().getId())
                .build();
    }
}
