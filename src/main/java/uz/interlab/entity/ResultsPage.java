package uz.interlab.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.form.Question;

import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
@Table(name = "results_page")
public class ResultsPage
{
    @Id
    Long id = 1L;

    String bannerTextUz;
    String bannerTextRu;

    @OneToMany(cascade = CascadeType.ALL)
    List<Question> question;

    String warningTextUz;
    String warningTextRu;

    String videoTitleTextUz;
    String videoTitleTextRu;

    String videoDescriptionTextUz;
    String videoDescriptionTextRu;

    String videoDescriptionLink;

    String videoIntroUrl;
    String videoUrl;
}
