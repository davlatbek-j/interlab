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

    boolean active;

    String detailsUrl;

    public DoctorDTO(Doctor entity, String lang)
    {
        this.id = entity.getId();
        this.photoUrl = entity.getPhotoUrl();
        this.active = entity.isActive();
        if (entity.getDetails() != null)
            this.detailsUrl = "http://localhost:8100/doctor-details/get?doctor-id=" + entity.getId() + "&lang=" + lang;

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
