package uz.interlab.payload;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.Banner;
import uz.interlab.exception.LanguageNotSupportException;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BannerDTO {

    Long id;

    String title;

    String description;

    boolean active;

    String photoUrl;

    String navigateToUrl;

    public BannerDTO(Banner banner, String lang) {
        this.id = banner.getId();
        this.photoUrl = banner.getPhotoUrl();
        this.navigateToUrl= banner.getNavigateToUrl();
        switch (lang.toLowerCase()) {

            case "uz": {
                this.title = banner.getTitleUz();
                this.description = banner.getDescriptionUz();
                break;
            }
            case "ru": {
                this.title = banner.getTitleRu();
                this.description = banner.getDescriptionRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);

        }
    }

}
