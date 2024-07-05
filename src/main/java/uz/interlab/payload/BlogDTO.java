package uz.interlab.payload;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.blog.Blog;
import uz.interlab.exception.LanguageNotSupportException;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogDTO {

    Long id;

    String title;

    String description;

    String body;

    String photoUrl;

    boolean active;

    List<LinkDTO> links;

    public BlogDTO(Blog blog, String lang) {
        this.id = blog.getId();
        this.photoUrl = blog.getPhotoUrl();
        this.active=blog.isActive();
        switch (lang.toLowerCase()) {

            case "uz": {
                this.title = blog.getTitleUz();
                this.description = blog.getDescriptionUz();
                this.body = blog.getBodyUz();
                break;
            }

            case "ru": {
                this.title = blog.getTitleRu();
                this.description = blog.getDescriptionRu();
                this.body = blog.getBodyRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported" + lang);
        }

        this.links = blog.getLinks().stream()
                .map(link -> new LinkDTO(link, lang))
                .collect(Collectors.toList());
    }


}
