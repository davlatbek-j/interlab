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

    @OneToOne
    Doctor doctor;

    @ElementCollection
    List<String> scheduleUz;

    @ElementCollection
    List<String> scheduleRu;

    @ElementCollection
    List<String> experienceUz;

    @ElementCollection
    List<String> experienceRu;

    @ElementCollection
    List<String> educationUz;

    @ElementCollection
    List<String> educationRu;

    String languageUz;

    String languageRu;

    @ElementCollection
    List<String> achievementUz;

    @ElementCollection
    List<String> achievementRu;

    @Enumerated(EnumType.STRING)
    Gender gender;
}
