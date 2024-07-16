package uz.interlab.payload.instruction;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.instruction.InstructionMainTitle;
import uz.interlab.exception.LanguageNotSupportException;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstructionMainTitleDTO
{
    Long id;

    String title;

    String description;

    String warning;

    public InstructionMainTitleDTO(InstructionMainTitle entity, String lang)
    {
        this.id = entity.getId();
        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.title = entity.getTitleUz();
                this.description = entity.getDescriptionUz();
                this.warning = entity.getWarningUz();
                break;
            }
            case "ru":
            {
                this.title = entity.getTitleRu();
                this.description = entity.getDescriptionRu();
                this.warning = entity.getWarningRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }
}
