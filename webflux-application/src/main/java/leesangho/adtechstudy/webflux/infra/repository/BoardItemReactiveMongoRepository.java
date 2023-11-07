package leesangho.adtechstudy.webflux.infra.repository;

import leesangho.adtechstudy.webflux.infra.document.BoardItemDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BoardItemReactiveMongoRepository extends ReactiveMongoRepository<BoardItemDocument, ObjectId> {
}