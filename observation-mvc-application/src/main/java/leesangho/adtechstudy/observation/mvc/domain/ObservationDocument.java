package leesangho.adtechstudy.observation.mvc.domain;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(value = "observation")
@Getter
@EqualsAndHashCode
@ToString
public class ObservationDocument {

  @Id
  private ObjectId id;

  @Field
  private String name;

  public ObservationDocument(String name) {
    this.name = name;
  }
}
