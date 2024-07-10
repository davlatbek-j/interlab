package uz.interlab.entity.legal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "legal_page_head")
public class LegalPageHead {

    @Id
    @GeneratedValue
    Long id;

    String titleUz;

    String titleRu;

    String subtitleUz;

    String subtitleRu;

    @Column(length = 500)
    String descriptionUz;

    @Column(length = 500)
    String descriptionRu;

    boolean active;

    String photoUrl;

}
