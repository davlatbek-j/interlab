package uz.interlab.payload;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.Entrance;
import uz.interlab.exception.LanguageNotSupportException;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EntranceDTO {

    Long id;

    String title;

    String description;

    boolean active;

    String photoUrl;

    public EntranceDTO(Entrance entrance, String lang) {
        this.id = entrance.getId();
        this.photoUrl = entrance.getPhotoUrl();
        switch (lang.toLowerCase()) {

            case "uz": {
                this.title = entrance.getTitleUz();
                this.description = entrance.getDescriptionUz();
                break;
            }
            case "ru": {
                this.title = entrance.getTitleRu();
                this.description = entrance.getDescriptionRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);

        }
    }

}
