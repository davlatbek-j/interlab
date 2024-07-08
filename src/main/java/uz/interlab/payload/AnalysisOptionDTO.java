package uz.interlab.payload;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.analysis.AnalysisOption;
import uz.interlab.exception.LanguageNotSupportException;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnalysisOptionDTO {

    Long id;

    String title;

    String description;

    Double price;

    String slug;

    boolean popular;

    public AnalysisOptionDTO(AnalysisOption analysisOption, String lang) {
        this.id = analysisOption.getId();
        this.price = analysisOption.getPrice();
        this.slug=analysisOption.getSlug();
        this.popular=analysisOption.isPopular();
        switch (lang.toLowerCase()) {

            case "uz": {
                this.title = analysisOption.getTitleUz();
                this.description = analysisOption.getDescriptionUz();
                break;
            }
            case "ru": {
                this.title = analysisOption.getTitleRu();
                this.description = analysisOption.getDescriptionRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);

        }
    }

}
