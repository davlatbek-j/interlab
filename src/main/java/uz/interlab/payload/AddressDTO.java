package uz.interlab.payload;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.Address;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressDTO {
    Long id;
    String name;

    String location;

    String workingTime;


    public AddressDTO(Address address,String lang){
        this.id=address.getId();
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
        }
    }
}
