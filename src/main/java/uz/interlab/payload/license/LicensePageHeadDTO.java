package uz.interlab.payload.license;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.license.LicensePageHead;
import uz.interlab.exception.LanguageNotSupportException;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LicensePageHeadDTO {

    Long id;

    String title;

    String subtitle;

    String description;

    boolean active;

    public LicensePageHeadDTO(LicensePageHead licensePageHead, String lang) {
        this.id = licensePageHead.getId();
        this.active=licensePageHead.isActive();
        switch (lang.toLowerCase()) {

            case "uz": {
                this.title = licensePageHead.getTitleUz();
                this.subtitle = licensePageHead.getSubtitleUz();
                this.description = licensePageHead.getDescriptionUz();
                break;
            }

            case "ru": {
                this.title = licensePageHead.getTitleRu();
                this.subtitle = licensePageHead.getSubtitleRu();
                this.description = licensePageHead.getDescriptionRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }

}
