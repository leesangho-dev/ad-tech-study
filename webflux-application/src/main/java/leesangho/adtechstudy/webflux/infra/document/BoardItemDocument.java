package leesangho.adtechstudy.webflux.infra.document;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("board")
@Getter
public class BoardItemDocument {

    @Id
    private String id;

    private String title;

    private String body;

    private String created;

    private String modified;

    protected BoardItemDocument() {
    }

    @Builder
    protected BoardItemDocument(String id, String title, String body, String created, String modified) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.created = created;
        this.modified = modified;
    }

}
