package uz.interlab.payload.legal;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.legal.LegalPageBody;
import uz.interlab.exception.LanguageNotSupportException;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LegalPageBodyDTO {

    Long id;

    String title;

    String description;

    boolean active;

    public LegalPageBodyDTO(LegalPageBody legalPageBody, String lang) {
        this.id = legalPageBody.getId();
        this.active = legalPageBody.isActive();
        switch (lang.toLowerCase()) {

            case "uz": {
                this.title = legalPageBody.getTitleUz();
                this.description = legalPageBody.getDescriptionUz();
                break;
            }
            case "ru": {
                this.title = legalPageBody.getTitleRu();
                this.description = legalPageBody.getDescriptionRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }

}
