package leesangho.adtechstudy.observation.webflux.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "observation")
@Getter
@EqualsAndHashCode
@ToString
public class ObservationDocument {

    @Id
    private ObjectId id;

    private String name;

    public ObservationDocument(String name) {
        this.name = name;
    }
}
