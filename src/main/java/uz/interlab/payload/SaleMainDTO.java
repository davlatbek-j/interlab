package uz.interlab.payload;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.sale.Sale;
import uz.interlab.exception.LanguageNotSupportException;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SaleMainDTO
{
    Long id;

    String slug;

    String name;

    String time;

    String mainPhoto;

    boolean main;

    boolean active;

    public SaleMainDTO(Sale entity, String lang)
    {
        this.id = entity.getId();
        this.slug = entity.getSlug();
        this.main = entity.isMain();
        this.active = entity.isActive();

        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.name = entity.getNameUz();
                this.time = entity.getTimeUz();
                this.mainPhoto = entity.getMainPhotoUz();
                break;
            }
            case "ru":
            {
                this.name = entity.getNameRu();
                this.time = entity.getTimeRu();
                this.mainPhoto = entity.getMainPhotoRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }
}
