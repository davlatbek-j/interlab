package uz.interlab.payload.doctor;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.doctor.DoctorDetails;
import uz.interlab.entity.doctor.Schedule;
import uz.interlab.entity.enums.Gender;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorDetailsDTO
{
    Long id;

    Schedule schedule;

    List<OccupationDTO> occupation;

    List<EducationDTO> education;

    List<TrainingDTO> training;

    String language;

    Gender gender;

    public DoctorDetailsDTO(DoctorDetails entity, String lang)
    {
        this.id = entity.getId();
        this.schedule = entity.getSchedule();
        this.gender = entity.getGender();
        this.occupation = new ArrayList<>();
        this.education = new ArrayList<>();
        this.training = new ArrayList<>();

        entity.getOccupation().forEach(i -> this.occupation.add(new OccupationDTO(i, lang)));
        entity.getEducation().forEach(i -> this.education.add(new EducationDTO(i, lang)));
        entity.getTraining().forEach(i -> this.training.add(new TrainingDTO(i, lang)));
    }
}
