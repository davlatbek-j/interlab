package uz.interlab.payload.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.service.ServiceDetails;
import uz.interlab.exception.LanguageNotSupportException;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceDetailsDTO
{
    Long serviceId;

    String name;

    String description;

    String photoUrl;

    String text;

    public ServiceDetailsDTO(ServiceDetails entity, String lang)
    {
        this.serviceId = entity.getService().getId();
        this.photoUrl = entity.getPhotoUrl();
        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.name = entity.getService().getNameUz();
                this.description = entity.getService().getDescriptionUz();
                this.text = entity.getTextUz();
                break;
            }
            case "ru":
            {
                this.name = entity.getService().getNameRu();
                this.description = entity.getService().getDescriptionRu();
                this.text = entity.getTextRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }
}
