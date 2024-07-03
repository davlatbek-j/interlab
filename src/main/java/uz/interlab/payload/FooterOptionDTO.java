package uz.interlab.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.footer.FooterOption;
import uz.interlab.exception.LanguageNotSupportException;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FooterOptionDTO
{
    Long id;
    String name;
    String url;

    public FooterOptionDTO(FooterOption entity, String lang)
    {
        this.id = entity.getId();
        this.url = entity.getUrl();
        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.name = entity.getNameUz();
                break;
            }
            case "ru":
            {
                this.name = entity.getNameRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }
}
