package leesangho.adtechstudy.observation.webflux.domain;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ObservationDocumentRepository extends
    ReactiveMongoRepository<ObservationDocument, ObjectId> {

}
