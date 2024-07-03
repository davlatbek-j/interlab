package uz.interlab.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "newness")
public class Newness {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String titleUz;

    String titleRu;

    String descriptionUz;

    String descriptionRu;

    String bodyUz;

    String bodyRu;

    String photoUrl;

    boolean active;

    @OneToMany(cascade = CascadeType.ALL)
    List<Link> links;

}
