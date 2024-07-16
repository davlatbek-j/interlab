package uz.interlab.payload;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.footer.Creator;
import uz.interlab.entity.footer.Footer;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FooterDTO
{
    Long id;

    String logoUrl;

    String telegramUrl;

    String instagramUrl;

    String youtubeUrl;

    String facebookUrl;

    String tgIconUrl;

    String youtubeIconUrl;

    String instaIconUrl;

    List<FooterOptionDTO> option;

    Creator creator;

    boolean active;

    public FooterDTO(Footer entity, String lang)
    {
        this.id = entity.getId();
        this.logoUrl = entity.getLogoUrl();
        this.telegramUrl = entity.getTelegramUrl();
        this.instagramUrl = entity.getInstagramUrl();
        this.youtubeUrl = entity.getYoutubeUrl();
        this.facebookUrl = entity.getFacebookUrl();
//        this.creator = entity.getCreator();
        this.active = entity.isActive();
        this.option = new ArrayList<>();
        entity.getFooterOption().forEach(i -> this.option.add(new FooterOptionDTO(i, lang)));
    }
}
