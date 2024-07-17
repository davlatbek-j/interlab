package uz.interlab.entity.form.mainPage;

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
//Оставьте заявку и мы перезвоним вам в ближайшее время
public class MainPageForm
{
    @Id
    Long id;

    String textUz;
    String textRu;

    @OneToMany(cascade = CascadeType.ALL)
    List<Question> question;
}

