package leesangho.adtechstudy.webflux.infra.document;

import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("board")
@Getter
public class BoardItemDocument {

    @Id
    private ObjectId id;

    private String title;

    private String body;

    private String created;

    private String modified;

    protected BoardItemDocument() {
    }

    @Builder
    protected BoardItemDocument(ObjectId id, String title, String body, String created, String modified) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.created = created;
        this.modified = modified;
    }

    public String idString() {
        return id.toString();
    }
}
