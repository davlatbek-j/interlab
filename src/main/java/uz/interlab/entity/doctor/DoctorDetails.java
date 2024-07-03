package uz.interlab.entity.doctor;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.enums.Gender;

import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
public class DoctorDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id", referencedColumnName = "id")
    Schedule schedule;

    @OneToMany(cascade = CascadeType.ALL)
    List<Occupation> occupation;

    @OneToMany(cascade = CascadeType.ALL)
    List<Education> education;

    @OneToMany(cascade = CascadeType.ALL)
    List<Training> training;

    String langUz;

    String langRu;

    @Enumerated(EnumType.STRING)
    Gender gender;
}
