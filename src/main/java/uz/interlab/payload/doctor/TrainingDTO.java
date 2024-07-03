package uz.interlab.payload.doctor;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.doctor.Training;
import uz.interlab.exception.LanguageNotSupportException;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainingDTO
{
    Long id;

    String name;

    public TrainingDTO(Training entity, String lang)
    {
        this.id = entity.getId();
        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.name = entity.getNameUz();
                break;
            }
            case "ru":
            {
                this.name = entity.getNameRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not support: " + lang);
        }
    }
}
