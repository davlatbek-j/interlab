package uz.interlab.payload.license;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.license.LicenseInfo;
import uz.interlab.exception.LanguageNotSupportException;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class LicenseInfoDTO {

    Long id;

    String title;

    String body;

    boolean active;

    public LicenseInfoDTO(LicenseInfo licenseInfo, String lang) {
        this.id = licenseInfo.getId();
        this.active = licenseInfo.isActive();

        switch (lang.toLowerCase()) {

            case "uz": {
                this.title = licenseInfo.getTitleUz();
                this.body = licenseInfo.getBodyUz();
                break;
            }

            case "ru": {
                this.title = licenseInfo.getTitleRu();
                this.body = licenseInfo.getBodyRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }

}
