package uz.interlab.entity.form;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
//Онлайн запись Doctor page
public class DoctorForm
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
