package uz.interlab.payload.form;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.form.vacancyPage.VacancyPageForm;
import uz.interlab.exception.LanguageNotSupportException;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VacancyPageFormDTO
{
    Long id;

    String title;

    String description;

    List<QuestionDTO> question;

    public VacancyPageFormDTO(VacancyPageForm entity, String lang)
    {
        this.id = entity.getId();
        this.question = new ArrayList<>();
        entity.getQuestion().forEach(i -> question.add(new QuestionDTO(i, lang)));
        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.title = entity.getTitleUz();
                this.description = entity.getDescriptionUz();
                break;
            }
            case "ru":
            {
                this.title = entity.getTitleRu();
                this.description = entity.getDescriptionRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }
}
