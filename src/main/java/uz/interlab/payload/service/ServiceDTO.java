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
public class ServiceDTO
{
    Long id;

    String slug;

    String name;

    String description;

    String iconUrl;

    String colourCode;

    boolean active;

    boolean main;

    public ServiceDTO(Service entity, String lang)
    {
        this.id = entity.getId();
        this.iconUrl = entity.getIconUrl();
        this.colourCode = entity.getColourCode();
        this.active = entity.isActive();
        this.main = entity.isMain();
        this.slug = entity.getSlug();

        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.name = entity.getNameUz();
                this.description = entity.getDescriptionUz();
                break;
            }
            case "ru":
            {
                this.name = entity.getNameRu();
                this.description = entity.getDescriptionRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }
}
