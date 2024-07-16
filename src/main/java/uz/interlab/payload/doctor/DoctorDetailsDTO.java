package uz.interlab.payload.doctor;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.doctor.Doctor;
import uz.interlab.entity.enums.Gender;
import uz.interlab.exception.LanguageNotSupportException;

import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorDetailsDTO
{
    Long doctorId;

    String slug;

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

    public DoctorDetailsDTO(Doctor entity, String lang)
    {
        this.doctorId = entity.getId();
        this.slug = entity.getSlug();
        this.gender = entity.getDetails().getGender();
        this.active = entity.isActive();
        this.main = entity.isMain();
        this.photoUrl = entity.getPhotoUrl();

        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.fullName = entity.getFullNameUz();
                this.speciality = entity.getSpecialityUz();
                this.schedule = entity.getDetails().getScheduleUz();
                this.experience = entity.getDetails().getExperienceUz();
                this.education = entity.getDetails().getEducationUz();
                this.language = entity.getDetails().getLanguageUz();
                this.achievement = entity.getDetails().getAchievementUz();
                break;
            }
            case "ru":
            {
                this.fullName = entity.getFullNameRu();
                this.speciality = entity.getSpecialityRu();
                this.schedule = entity.getDetails().getScheduleRu();
                this.experience = entity.getDetails().getExperienceRu();
                this.education = entity.getDetails().getEducationRu();
                this.language = entity.getDetails().getLanguageRu();
                this.achievement = entity.getDetails().getAchievementRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }
}
