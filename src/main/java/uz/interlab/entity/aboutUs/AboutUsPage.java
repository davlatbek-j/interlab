package uz.interlab.entity.aboutUs;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity(name = "about_us_page")
public class AboutUsPage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String titleUz;

    String titleRu;

    String subtitleUz;

    String subtitleRu;

    String descriptionUz;

    String descriptionRu;

    boolean active;

    List<String> photoUrls;

}
