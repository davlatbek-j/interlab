package uz.interlab.payload.instruction;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.instruction.InstructionProperty;
import uz.interlab.exception.LanguageNotSupportException;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstructionPropertyDTO
{
    Long id;

    String name;

    String text;

    public InstructionPropertyDTO(InstructionProperty entity, String lang)
    {
        this.id = entity.getId();
        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.name = entity.getNameUz();
                this.text = entity.getNameUz();
                break;
            }
            case "ru":
            {
                this.name = entity.getNameRu();
                this.text = entity.getNameRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }
}
