package uz.interlab.payload;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.analysis.AnalysisDetails;
import uz.interlab.entity.analysis.AnalysisOption;
import uz.interlab.exception.LanguageNotSupportException;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnalysisDetailsDTO {

    Long id;

    AnalysisOptionDTO analysisOptionDTO;

    String body;

    boolean active;

    public AnalysisDetailsDTO(AnalysisDetails details, String lang) {
        this.id = details.getId();
        this.active= details.isActive();
        switch (lang.toLowerCase()) {
            case "uz": {
                this.body = details.getBodyUz();
                break;
            }
            case "ru": {
                this.body = details.getBodyRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language supported: " + lang);
        }
        this.analysisOptionDTO = new AnalysisOptionDTO(details.getAnalysisOption(), lang);
    }

}
