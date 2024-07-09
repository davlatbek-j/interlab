package uz.interlab.payload;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.Achievement;
import uz.interlab.exception.LanguageNotSupportException;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AchievementDTO {

    Long id;

    String title;

    String description;

    boolean active;

    public AchievementDTO(Achievement achievement, String lang) {
        this.id = achievement.getId();
        this.active=achievement.isActive();
        switch (lang.toLowerCase()) {

            case "uz": {
                this.title = achievement.getTitleUz();
                this.description = achievement.getDescriptionUz();
                break;
            }

            case "ru": {
                this.title = achievement.getTitleRu();
                this.description = achievement.getDescriptionRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }

}
