package uz.interlab.payload.instruction;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.instruction.Instruction;
import uz.interlab.exception.LanguageNotSupportException;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstructionDetailsDTO
{
    Long id;

    String slug;

    String name;

    String description;

    List<InstructionPropertyDTO> property;

    public InstructionDetailsDTO(Instruction entity, String lang)
    {
        this.id = entity.getId();
        this.slug = entity.getSlug();
        this.property = new ArrayList<>();
        entity.getDetails().getProperty().forEach(i -> this.property.add(new InstructionPropertyDTO(i, lang)));
        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.name = entity.getNameUz();
                this.description = entity.getDetails().getDescriptionUz();
                break;
            }
            case "ru":
            {
                this.name = entity.getNameRu();
                this.description = entity.getDetails().getDescriptionRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }
}
