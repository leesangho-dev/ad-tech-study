package leesangho.adtechstudy.observation.mvc.domain;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ObservationDocumentRepository extends
    MongoRepository<ObservationDocument, ObjectId> {

}
