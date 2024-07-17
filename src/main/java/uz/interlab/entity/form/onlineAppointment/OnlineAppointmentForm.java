package uz.interlab.entity.form.onlineAppointment;

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
//Записаться на приём онлайн и получить услугу минуя живую очередь
public class OnlineAppointmentForm
{
    @Id
    Long id;

    String titleUz;
    String titleRu;

    String subTitleUz;
    String subTitleRu;

    String descriptionUz;
    String descriptionRu;

    @OneToMany(cascade = CascadeType.ALL)
    List<Question> question;

}
