package uz.interlab.payload;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.InstructionOption;
import uz.interlab.exception.LanguageNotSupportException;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InstructionOptionDto {

    Long id;

    String description;

    public InstructionOptionDto(InstructionOption option, String lang){
        this.id=option.getId();
        switch (lang.toLowerCase()){

            case "uz":{
                this.description= option.getDescriptionUz();
                break;
            }

            case "ru":{
                this.description= option.getDescriptionRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: "+lang);

        }
    }

}
