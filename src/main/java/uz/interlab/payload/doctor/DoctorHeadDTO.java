package uz.interlab.payload.doctor;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.doctor.DoctorHead;
import uz.interlab.exception.LanguageNotSupportException;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoctorHeadDTO
{
    Long id;

    String title;

    String subTitle;

    String description;

    String photoUrl;

    String colourCode;

    public DoctorHeadDTO(DoctorHead entity, String lang)
    {
        this.id = entity.getId();
        this.photoUrl = entity.getPhotoUrl();
        this.colourCode = entity.getColourCode();
        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.title = entity.getTitleUz();
                this.subTitle = entity.getSubTitleUz();
                this.description = entity.getDescriptionUz();
                break;
            }
            case "ru":
            {
                this.title = entity.getTitleRu();
                this.subTitle = entity.getSubTitleRu();
                this.description = entity.getDescriptionRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }
}