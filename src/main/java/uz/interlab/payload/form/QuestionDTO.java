package uz.interlab.payload.form;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.form.Question;
import uz.interlab.exception.LanguageNotSupportException;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionDTO
{
    Long id;

    String name;

    boolean required;

    boolean optional;

    List<String> option;

    public QuestionDTO(Question entity, String lang)
    {
        this.id = entity.getId();
        this.required = entity.isRequired();
        this.option = new ArrayList<>();
        this.optional = entity.isOptional();
        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.name = entity.getNameUz();
                option.addAll(entity.getOptionUz());
                break;
            }
            case "ru":
            {
                this.name = entity.getNameRu();
                option.addAll(entity.getOptionRu());
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }
}
