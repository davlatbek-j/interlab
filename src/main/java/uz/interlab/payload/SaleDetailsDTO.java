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
public class SaleDetailsDTO
{
    Long id;

    String slug;

    String name;

    String time;

    boolean main;

    boolean active;

    String backgroundPhoto;

    String icon;

    String text;

    public SaleDetailsDTO(Sale entity, String lang)
    {
        this.id = entity.getId();
        this.slug = entity.getSlug();
        this.backgroundPhoto = entity.getDetails().getBackgroundPhoto();
        this.icon = entity.getDetails().getIcon();
        this.main = entity.isMain();
        this.active = entity.isActive();
        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.name = entity.getNameUz();
                this.time = entity.getTimeUz();
                this.text = entity.getDetails().getTextUz();
                break;
            }
            case "ru":
            {
                this.name = entity.getNameRu();
                this.time = entity.getTimeRu();
                this.text = entity.getDetails().getTextRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }
}
