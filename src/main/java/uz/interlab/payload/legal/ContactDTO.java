package uz.interlab.payload.legal;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.legal.Contact;
import uz.interlab.exception.LanguageNotSupportException;

import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContactDTO {

    Long id;

    String title;

    String description;

    List<String> phoneNumbers;

    String buttonCall;

    public ContactDTO(Contact contact, String lang){
        this.id= contact.getId();
        this.phoneNumbers=contact.getPhoneNumbers();
        this.buttonCall=contact.getButtonCall();

        switch (lang.toLowerCase()){

            case "uz":{
                this.title=contact.getTitleUz();
                this.description=contact.getDescriptionUz();
                break;
            }
            case "ru":{
                this.title=contact.getTitleRu();
                this.description=contact.getDescriptionRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: "+lang);
        }
    }

}
