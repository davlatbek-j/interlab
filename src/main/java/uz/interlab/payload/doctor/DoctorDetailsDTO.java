package uz.interlab.payload.doctor;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.doctor.DoctorDetails;
import uz.interlab.entity.enums.Gender;
import uz.interlab.exception.LanguageNotSupportException;

import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorDetailsDTO
{
    Long doctorId;

    String fullName;

    List<String> speciality;

    String photoUrl;
    ///////////////////////////////
    List<String> schedule;

    List<String> experience;

    List<String> education;

    String language;

    List<String> achievement;

    Gender gender;

    boolean main;

    boolean active;

    public DoctorDetailsDTO(DoctorDetails entity, String lang)
    {
        this.doctorId = entity.getDoctor().getId();
        this.gender = entity.getGender();
        this.active = entity.getDoctor().isActive();
        this.main = entity.getDoctor().isMain();
        this.photoUrl = entity.getDoctor().getPhotoUrl();

        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.fullName = entity.getDoctor().getFullNameUz();
                this.speciality = entity.getDoctor().getSpecialityUz();
                this.schedule = entity.getScheduleUz();
                this.experience = entity.getExperienceUz();
                this.education = entity.getEducationUz();
                this.language = entity.getLanguageUz();
                this.achievement = entity.getAchievementUz();
                break;
            }
            case "ru":
            {
                this.fullName = entity.getDoctor().getFullNameRu();
                this.speciality = entity.getDoctor().getSpecialityRu();
                this.schedule = entity.getScheduleRu();
                this.experience = entity.getExperienceRu();
                this.education = entity.getEducationRu();
                this.language = entity.getLanguageRu();
                this.achievement = entity.getAchievementRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }
}
