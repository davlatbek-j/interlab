package uz.interlab.payload.vacancy;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.vacancy.Vacancy;
import uz.interlab.exception.LanguageNotSupportException;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VacancyDetailsDTO
{
    Long id;

    String slug;

    String name;

    String title;

    String subTitle;

    String description;

    List<VacancyPropertiesDTO> properties;

    public VacancyDetailsDTO(Vacancy entity, String lang)
    {
        this.id = entity.getId();
        this.slug = entity.getSlug();
        this.properties = new ArrayList<>();
        entity.getDetails().getProperties().forEach(i -> this.properties.add(new VacancyPropertiesDTO(i, lang)));
        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.name = entity.getNameUz();
                this.title = entity.getDetails().getTitleUz();
                this.subTitle = entity.getDetails().getTitleUz();
                this.description = entity.getDetails().getDescriptionUz();
                break;
            }
            case "ru":
            {
                this.name = entity.getNameRu();
                this.title = entity.getDetails().getTitleRu();
                this.subTitle = entity.getDetails().getTitleRu();
                this.description = entity.getDetails().getDescriptionRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }
}
