package uz.interlab.payload.doctor;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.doctor.Occupation;
import uz.interlab.exception.LanguageNotSupportException;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OccupationDTO
{
    Long id;

    String name;

    public OccupationDTO(Occupation entity, String lang)
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
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }
}
