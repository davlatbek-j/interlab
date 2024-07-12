package uz.interlab.payload.vacancy;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.vacancy.VacancyProperties;
import uz.interlab.exception.LanguageNotSupportException;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VacancyPropertiesDTO
{
    Long id;

    String name;

    List<String> option;

    public VacancyPropertiesDTO(VacancyProperties entity, String lang)
    {
        this.id = entity.getId();
        this.option = new ArrayList<>();
        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.name = entity.getNameUz();
                this.option.addAll(entity.getOptionUz());
                break;
            }
            case "ru":
            {
                this.name = entity.getNameRu();
                this.option.addAll(entity.getOptionRu());
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }
}
