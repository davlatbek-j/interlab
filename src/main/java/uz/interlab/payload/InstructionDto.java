package uz.interlab.payload;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.Instruction;
import uz.interlab.entity.InstructionOption;
import uz.interlab.entity.Photo;
import uz.interlab.exception.LanguageNotSupportException;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstructionDto {
    Long id;

    String name;

    boolean active;

    List<InstructionOptionDto> options;

    public InstructionDto(Instruction instruction, String lang) {
        this.id = instruction.getId();
        this.active=instruction.isActive();
        switch (lang.toLowerCase()) {

            case "uz": {
                this.name = instruction.getNameUz();
                break;
            }
            case "ru": {
                this.name=instruction.getNameRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: "+lang);
        }

        this.options=instruction.getOptions().stream()
                .map(option -> new InstructionOptionDto(option,lang))
                .collect(Collectors.toList());
    }
}
