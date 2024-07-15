package uz.interlab.payload;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.ProfileMyAddress;
import uz.interlab.exception.LanguageNotSupportException;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileMyAddressDTO {

    Long id;

    String title;

    String addressInfo;

    boolean active;

    public ProfileMyAddressDTO(ProfileMyAddress profileMyAddress, String lang) {
        this.id = profileMyAddress.getId();
        this.active = profileMyAddress.isActive();
        switch (lang.toLowerCase()) {

            case "uz": {
                this.title = profileMyAddress.getTitleUz();
                this.addressInfo = profileMyAddress.getAddressInfoUz();
                break;
            }

            case "ru": {
                this.title = profileMyAddress.getTitleRu();
                this.addressInfo = profileMyAddress.getAddressInfoRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);

        }
    }

}
