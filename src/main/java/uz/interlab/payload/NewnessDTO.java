package uz.interlab.payload;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.news.Newness;
import uz.interlab.exception.LanguageNotSupportException;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewnessDTO {

    Long id;

    String title;

    String description;

    String body;

    String photoUrl;

    boolean active;

    List<LinkDTO> links;

    public NewnessDTO(Newness newness, String lang) {
        this.id = newness.getId();
        this.photoUrl = newness.getPhotoUrl();

        switch (lang.toLowerCase()) {

            case "uz": {
                this.title = newness.getTitleUz();
                this.description = newness.getDescriptionUz();
                this.body = newness.getBodyUz();
                break;
            }

            case "ru": {
                this.title = newness.getTitleRu();
                this.description = newness.getDescriptionRu();
                this.body = newness.getBodyRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported" + lang);
        }

        this.links = newness.getLinks().stream()
                .map(link -> new LinkDTO(link, lang))
                .collect(Collectors.toList());
    }


}
