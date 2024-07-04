package uz.interlab.payload;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.news.Link;
import uz.interlab.exception.LanguageNotSupportException;


@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LinkDTO {

    Long id;

    String name;

    String url;

    public LinkDTO(Link link, String lang) {
        this.id = link.getId();
        this.url = link.getUrl();

        switch (lang.toLowerCase()) {

            case "uz": {
                this.name = link.getNameUz();
                break;
            }

            case "ru": {
                this.name = link.getNameRu();
                break;
            }

            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);

        }
    }

}
