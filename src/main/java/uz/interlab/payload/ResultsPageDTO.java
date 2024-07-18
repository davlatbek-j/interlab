package uz.interlab.payload;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.ResultsPage;
import uz.interlab.exception.LanguageNotSupportException;
import uz.interlab.payload.form.QuestionDTO;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResultsPageDTO
{
    Long id;

    String bannerText;

    List<QuestionDTO> question;

    String warningText;

    String videoTitleText;

    String videoDescriptionText;

    String videoDescriptionLink;

    String videoIntroUrl;

    String videoUrl;

    public ResultsPageDTO(ResultsPage entity, String lang)
    {
        this.id = entity.getId();
        this.question = new ArrayList<>();
        entity.getQuestion().forEach(i -> this.question.add(new QuestionDTO(i, lang)));
        this.videoDescriptionLink = entity.getVideoDescriptionLink();
        this.videoIntroUrl = entity.getVideoIntroUrl();
        this.videoUrl = entity.getVideoUrl();

        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.bannerText = entity.getBannerTextUz();
                this.warningText = entity.getWarningTextUz();
                this.videoTitleText = entity.getVideoTitleTextUz();
                this.videoDescriptionText = entity.getVideoDescriptionTextUz();
                break;
            }
            case "ru":
            {
                this.bannerText = entity.getBannerTextRu();
                this.warningText = entity.getWarningTextRu();
                this.videoTitleText = entity.getVideoTitleTextRu();
                this.videoDescriptionText = entity.getVideoDescriptionTextRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }

    }
}
