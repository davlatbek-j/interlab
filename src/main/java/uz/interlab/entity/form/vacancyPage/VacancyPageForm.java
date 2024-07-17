package uz.interlab.entity.form.vacancyPage;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.form.Question;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
public class VacancyPageForm
{
    @Id
    Long id;

    String titleUz;
    String titleRu;

    String descriptionUz;
    String descriptionRu;

    @OneToMany(cascade = CascadeType.ALL)
    List<Question> question;
}
