package uz.interlab.payload.vacancy;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.vacancy.VacancyBody;
import uz.interlab.exception.LanguageNotSupportException;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VacancyBodyDTO {

    Long id;

    String title;

    String description;

    String iconUrl;

    boolean active;

    public VacancyBodyDTO(VacancyBody vacancyBody, String lang) {
        this.id = vacancyBody.getId();
        this.iconUrl = vacancyBody.getIconUrl();
        this.active = vacancyBody.isActive();

        switch (lang.toLowerCase()) {

            case "uz": {
                this.title = vacancyBody.getTitleUz();
                this.description = vacancyBody.getDescriptionUz();
                break;
            }

            case "ru": {
                this.title = vacancyBody.getTitleRu();
                this.description = vacancyBody.getDescriptionRu();
                break;
            }

            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);

        }
    }

}
