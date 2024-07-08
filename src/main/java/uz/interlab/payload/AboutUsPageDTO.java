package uz.interlab.payload;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.aboutUs.AboutUsPage;
import uz.interlab.exception.LanguageNotSupportException;

import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AboutUsPageDTO {

    Long id;

    String title;

    String subtitle;

    String description;

    boolean active;

    List<String> photoUrls;

    public AboutUsPageDTO(AboutUsPage aboutUsPage, String lang) {
        this.id = aboutUsPage.getId();
        this.photoUrls = aboutUsPage.getPhotoUrls();
        this.active= aboutUsPage.isActive();
        switch (lang.toLowerCase()) {

            case "uz": {
                this.title = aboutUsPage.getTitleUz();
                this.subtitle = aboutUsPage.getSubtitleUz();
                this.description = aboutUsPage.getDescriptionUz();
                break;
            }

            case "ru": {
                this.title = aboutUsPage.getTitleRu();
                this.subtitle = aboutUsPage.getSubtitleRu();
                this.description = aboutUsPage.getDescriptionRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }

}
