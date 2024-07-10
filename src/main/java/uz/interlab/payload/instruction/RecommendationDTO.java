package uz.interlab.payload.instruction;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.instruction.Recommendation;
import uz.interlab.exception.LanguageNotSupportException;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecommendationDTO
{
    Long id;

    String name;

    List<String> value;

    public RecommendationDTO(Recommendation entity, String lang)
    {
        this.id = entity.getId();
        this.value = new ArrayList<>();
        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.name = entity.getNameUz();
                this.value.addAll(entity.getValueUz());
                break;
            }
            case "ru":
            {
                this.name = entity.getNameRu();
                this.value.addAll(entity.getValueRu());
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }
}
