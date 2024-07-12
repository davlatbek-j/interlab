package uz.interlab.entity.license;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "license_page_head")
public class LicensePageHead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

}
