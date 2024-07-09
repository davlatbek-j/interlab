package uz.interlab.payload.instruction;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.instruction.Instruction;
import uz.interlab.exception.LanguageNotSupportException;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstructionDto
{
    Long id;

    String name;

    boolean active;

    public InstructionDto(Instruction instruction, String lang)
    {
        this.id = instruction.getId();
        this.active = instruction.isActive();
        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.name = instruction.getNameUz();
                break;
            }
            case "ru":
            {
                this.name = instruction.getNameRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }
}
