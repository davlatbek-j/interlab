package uz.interlab.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.Service;
import uz.interlab.exception.LanguageNotSupportException;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceDTO
{
    Long id;

    String name;

    String description;

    String iconUrl;

    String detailsUrl;

    Boolean active;

    public ServiceDTO(Service entity, String lang)
    {
        this.id = entity.getId();
        this.iconUrl = entity.getIconUrl();
        this.detailsUrl = entity.getDetailsUrl();
        this.active = entity.getActive();
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
