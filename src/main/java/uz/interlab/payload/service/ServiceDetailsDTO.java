package uz.interlab.payload.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.service.Service;
import uz.interlab.exception.LanguageNotSupportException;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceDetailsDTO
{
    Long serviceId;

    String slug;

    String name;

    String description;

    String text;

    public ServiceDetailsDTO(Service entity, String lang)
    {
        this.serviceId = entity.getId();
        this.slug = entity.getSlug();
        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.name = entity.getNameUz();
                this.description = entity.getDescriptionUz();
                this.text = entity.getDetails().getTextUz();
                break;
            }
            case "ru":
            {
                this.name = entity.getNameRu();
                this.description = entity.getDescriptionRu();
                this.text = entity.getDetails().getTextRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }
}
