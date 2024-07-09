package uz.interlab.payload.license;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.license.LicensePhoto;
import uz.interlab.exception.LanguageNotSupportException;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LicensePhotoDTO {

    Long id;

    String title;

    String body;

    String photoUrl;

    boolean active;

    public LicensePhotoDTO(LicensePhoto licensePhoto, String lang) {
        this.id = licensePhoto.getId();
        this.photoUrl = licensePhoto.getPhotoUrl();
        this.active = licensePhoto.isActive();
        switch (lang.toLowerCase()) {

            case "uz": {
                this.title = licensePhoto.getTitleUz();
                this.body = licensePhoto.getBodyUz();
                break;
            }

            case "ru": {
                this.title = licensePhoto.getTitleRu();
                this.body = licensePhoto.getBodyRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }

}
