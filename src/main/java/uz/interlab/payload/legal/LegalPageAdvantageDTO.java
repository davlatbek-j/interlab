package uz.interlab.payload.legal;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.legal.LegalPageAdvantage;
import uz.interlab.exception.LanguageNotSupportException;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LegalPageAdvantageDTO {

    Long id;

    String body;

    boolean active;

    public LegalPageAdvantageDTO(LegalPageAdvantage legalPageAdvantage, String lang) {
        this.id = legalPageAdvantage.getId();
        this.active = legalPageAdvantage.isActive();

        switch (lang.toLowerCase()) {

            case "uz": {
                this.body = legalPageAdvantage.getBodyUz();
                break;
            }
            case "ru": {
                this.body = legalPageAdvantage.getBodyRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not found by id: " + lang);
        }
    }

}
