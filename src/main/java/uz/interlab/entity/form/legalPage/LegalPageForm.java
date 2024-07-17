package uz.interlab.entity.form.legalPage;

import jakarta.persistence.*;
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
//Оставить заявку Заполните форму , чтобы оставить заявку на консультацию и обсудить условия сотрудничества. Мы свяжемся с вами в ближайшее время
public class LegalPageForm
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
