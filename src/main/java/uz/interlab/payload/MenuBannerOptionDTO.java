package uz.interlab.payload;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.menuBanner.MenuBannerOption;
import uz.interlab.exception.LanguageNotSupportException;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MenuBannerOptionDTO {

    Long id;

    String name;

    String url;

    public MenuBannerOptionDTO(MenuBannerOption option, String lang){
        this.id= option.getId();
        this.url= option.getUrl();
        switch (lang.toLowerCase()){

            case "uz":{
                this.name= option.getNameUz();
                break;
            }

            case "ru":{
                this.name= option.getNameRu();
                break;
            }
            default:
                throw new LanguageNotSupportException("Language not supported: "+lang);

        }
    }

}
