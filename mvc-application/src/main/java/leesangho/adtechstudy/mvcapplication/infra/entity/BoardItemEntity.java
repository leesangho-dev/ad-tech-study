package leesangho.adtechstudy.mvcapplication.infra.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "board")
@Getter
public class BoardItemEntity {

    @Id
    private String id;

    @Column(length = 100)
    private String title;

    @Column(length = 4000)
    private String body;

    @Column(length = 100)
    private String created;

    @Column(length = 100)
    private String modified;

    protected BoardItemEntity() {
    }

    @Builder
    protected BoardItemEntity(String id, String title, String body, String created, String modified) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.created = created;
        this.modified = modified;
    }
}
