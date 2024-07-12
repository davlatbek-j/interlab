package uz.interlab.payload.vacancy;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.vacancy.Vacancy;
import uz.interlab.exception.LanguageNotSupportException;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VacancyDTO
{
    Long id;

    String slug;

    String name;

    boolean main;

    boolean active;

    public VacancyDTO(Vacancy entity, String lang)
    {
        this.id = entity.getId();
        this.slug = entity.getSlug();
        this.main = entity.isMain();
        this.active = entity.isActive();
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
