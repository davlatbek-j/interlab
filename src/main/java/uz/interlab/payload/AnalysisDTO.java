package uz.interlab.payload;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.analysis.Analysis;
import uz.interlab.entity.analysis.AnalysisOption;
import uz.interlab.exception.LanguageNotSupportException;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnalysisDTO {

    Long id;

    String titleUz;

    String titleRu;

    String category;

    boolean active;

    List<AnalysisOptionDTO> analysisOptions;

    public AnalysisDTO(Analysis analysis, String lang) {
        this.id = analysis.getId();
        this.category = analysis.getCategory();
        this.active=analysis.isActive();
        switch (lang.toLowerCase()) {

            case "uz": {
                this.titleUz = analysis.getTitleUz();
                break;
            }
            case "ru": {
                this.titleRu = analysis.getTitleRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
        this.analysisOptions=analysis.getAnalysisOptions().stream()
                .map(analysisOption -> new AnalysisOptionDTO(analysisOption,lang))
                .collect(Collectors.toList());
    }


}
