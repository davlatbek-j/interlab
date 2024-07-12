package uz.interlab.payload.vacancy;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.vacancy.VacancyHead;
import uz.interlab.exception.LanguageNotSupportException;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VacancyHeadDTO {

    Long id;

    String title;

    String subtitle;

    String description;

    String photoUrl;

    boolean active;

    public VacancyHeadDTO(VacancyHead vacancyHead, String lang) {
        this.id = vacancyHead.getId();
        this.photoUrl = vacancyHead.getPhotoUrl();
        this.active = vacancyHead.isActive();

        switch (lang.toLowerCase()) {

            case "uz": {
                this.title = vacancyHead.getTitleUz();
                this.subtitle = vacancyHead.getSubtitleUz();
                this.description = vacancyHead.getDescriptionUz();
                break;
            }

            case "ru": {
                this.title = vacancyHead.getTitleRu();
                this.subtitle = vacancyHead.getSubtitleRu();
                this.description = vacancyHead.getDescriptionRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }

}
