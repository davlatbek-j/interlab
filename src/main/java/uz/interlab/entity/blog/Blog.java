package uz.interlab.entity.blog;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "blog")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String titleUz;

    String titleRu;

    @Column(length = 1000)
    String descriptionUz;

    @Column(length = 1000)
    String descriptionRu;

    @Column(length = 5000)
    String bodyUz;

    @Column(length = 5000)
    String bodyRu;

    String photoUrl;

    boolean active;

    String slug;

}
