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
@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstructionDto {
    Long id;

    String name;

    List<InstructionOption> option;

    public InstructionDto(Instruction entity)
    {
        this.id = entity.getId();
        this.name = entity.getName();
        this.option = entity.getOption();
    }
}
