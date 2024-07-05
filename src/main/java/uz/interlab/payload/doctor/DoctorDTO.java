package uz.interlab.payload.doctor;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.doctor.Doctor;
import uz.interlab.exception.LanguageNotSupportException;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorDTO
{
    Long id;

    String fullName;

    String speciality;

    String photoUrl;

    boolean main;

    boolean active;

    public DoctorDTO(Doctor entity, String lang)
    {
        this.id = entity.getId();
        this.photoUrl = entity.getPhotoUrl();
        this.main = entity.isMain();
        this.active = entity.isActive();

        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.fullName = entity.getFullNameUz();
                this.speciality = entity.getSpecialityUz();
                break;
            }
            case "ru":
            {
                this.fullName = entity.getFullNameRu();
                this.speciality = entity.getSpecialityRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }
}
