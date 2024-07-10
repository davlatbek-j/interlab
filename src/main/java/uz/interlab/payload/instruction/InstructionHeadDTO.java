package uz.interlab.payload.instruction;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.instruction.InstructionHead;
import uz.interlab.exception.LanguageNotSupportException;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstructionHeadDTO
{
    Long id;

    String title;

    String description;

    String colourCode;

    String iconUrl;

    public InstructionHeadDTO(InstructionHead entity, String lang)
    {
        this.id = entity.getId();
        this.colourCode = entity.getColourCode();
        this.iconUrl = entity.getIconUrl();

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
