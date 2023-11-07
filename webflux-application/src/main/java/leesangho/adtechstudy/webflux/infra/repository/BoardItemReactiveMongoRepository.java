package leesangho.adtechstudy.webflux.infra.repository;

import leesangho.adtechstudy.webflux.infra.document.BoardItemDocument;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface BoardItemReactiveMongoRepository extends ReactiveMongoRepository<BoardItemDocument, String> {

    Flux<BoardItemDocument> findAll(Pageable pageable);
}
