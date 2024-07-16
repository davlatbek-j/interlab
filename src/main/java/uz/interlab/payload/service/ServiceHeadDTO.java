package uz.interlab.payload.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.service.ServiceHead;
import uz.interlab.exception.LanguageNotSupportException;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceHeadDTO
{
    Long id;

    String title;

    String subTitle;

    String description;

    List<String> gallery;

    public ServiceHeadDTO(ServiceHead entity, String lang)
    {
        this.id = entity.getId();
        this.gallery = new ArrayList<>();
        this.gallery.addAll(entity.getGallery());
        switch (lang.toLowerCase())
        {
            case "uz":
            {
                this.title = entity.getTitleUz();
                this.subTitle = entity.getSubTitleUz();
                this.description = entity.getDescriptionUz();
                break;
            }
            case "ru":
            {
                this.title = entity.getTitleRu();
                this.subTitle = entity.getSubTitleRu();
                this.description = entity.getDescriptionRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: " + lang);
        }
    }
}
