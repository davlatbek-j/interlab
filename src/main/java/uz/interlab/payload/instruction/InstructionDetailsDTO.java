package uz.interlab.payload.instruction;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.instruction.InstructionDetails;
import uz.interlab.exception.LanguageNotSupportException;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstructionDetailsDTO
{
    Long instructionId;

    String name;

    String description;

    List<InstructionPropertyDTO> property;

    public InstructionDetailsDTO(InstructionDetails entity, String lang)
    {
        this.instructionId = entity.getInstruction().getId();
        this.property = new ArrayList<>();
        entity.getProperty().forEach(i -> this.property.add(new InstructionPropertyDTO(i, lang)));
        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.name = entity.getInstruction().getNameUz();
                this.description = entity.getDescriptionUz();
                break;
            }
            case "ru":
            {
                this.name = entity.getInstruction().getNameRu();
                this.description = entity.getDescriptionRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }
}
