package uz.interlab.payload;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.Address;
import uz.interlab.exception.LanguageNotSupportException;

import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressDTO {

    Long id;

    String name;

    String location;

    List<String> workingTime;

    String slug;

    boolean active;


    public AddressDTO(Address address,String lang){
        this.id=address.getId();
        this.slug=address.getSlug();
        this.active=address.isActive();
        switch (lang.toLowerCase()){
            case "uz" ->{
                this.name=address.getNameUz();
                this.location=address.getLocationUz();
                this.workingTime=address.getWorkingTimeUz();
            }
            case "ru" ->{
                this.name=address.getNameRu();
                this.location=address.getLocationRu();
                this.workingTime=address.getWorkingTimeUz();
            }
            default -> {
                throw new LanguageNotSupportException("Language not supported" + lang);
            }
        }
    }
}
