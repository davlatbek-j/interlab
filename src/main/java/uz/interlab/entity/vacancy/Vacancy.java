package uz.interlab.entity.vacancy;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Vacancy
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String slug;

    String nameUz;

    String nameRu;

    @OneToOne(cascade = CascadeType.ALL)
    VacancyDetails details;

    boolean main;

    boolean active;
}

