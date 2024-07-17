package uz.interlab.payload.form;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.form.MainPageForm;
import uz.interlab.exception.LanguageNotSupportException;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MainPageFormDTO
{
    Long id;

    String text;

    String colourCode;

    List<QuestionDTO> question;

    public MainPageFormDTO(MainPageForm entity, String lang)
    {
        this.id = entity.getId();
        this.question = new ArrayList<>();
        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.text = entity.getTextUz();
                break;
            }
            case "ru":
            {
                this.text = entity.getTextRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
        entity.getQuestion().forEach(i -> this.question.add(new QuestionDTO(i, lang)));
    }
}
