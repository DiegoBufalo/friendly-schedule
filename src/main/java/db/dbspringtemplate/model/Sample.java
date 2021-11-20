package db.dbspringtemplate.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "sample")
public class Sample {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pooled_optimizer")
    @SequenceGenerator(name = "pooled_optimizer", sequenceName = "sample_id_seq")
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
