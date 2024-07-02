package uz.interlab.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.ServiceDetails;
import uz.interlab.exception.LanguageNotSupportException;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceDetailsDTO
{
    Long id;

    String name;

    String description;

    String text;

    String photoUrl;

    public ServiceDetailsDTO(ServiceDetails entity, String lang)
    {
        this.id = entity.getId();
        this.photoUrl = entity.getPhotoUrl();

        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.name = entity.getNameUz();
                this.description = entity.getDescriptionUz();
                this.text = entity.getTextUz();
                break;
            }
            case "ru":
            {
                this.name = entity.getNameRu();
                this.description = entity.getDescriptionRu();
                this.text = entity.getTextRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported :" + lang);
        }
    }
}
