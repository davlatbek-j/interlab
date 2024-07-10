package uz.interlab.payload.legal;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.legal.LegalPageHead;
import uz.interlab.exception.LanguageNotSupportException;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LegalPageHeadDTO {

    Long id;

    String title;

    String subtitle;

    String description;

    boolean active;

    String photoUrl;

    public LegalPageHeadDTO(LegalPageHead legalPageHead, String lang) {
        this.id = legalPageHead.getId();
        this.photoUrl = legalPageHead.getPhotoUrl();
        this.active = legalPageHead.isActive();
        switch (lang.toLowerCase()) {

            case "uz": {
                this.title = legalPageHead.getTitleUz();
                this.subtitle = legalPageHead.getSubtitleUz();
                this.description = legalPageHead.getDescriptionUz();
                break;
            }

            case "ru": {
                this.title = legalPageHead.getTitleRu();
                this.subtitle = legalPageHead.getSubtitleRu();
                this.description = legalPageHead.getDescriptionRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);

        }
    }

}
