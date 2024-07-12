package uz.interlab.entity.vacancy;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class VacancyProperties
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String nameUz;
    String nameRu;

    @ElementCollection
    List<String> optionUz;

    @ElementCollection
    List<String> optionRu;
}
