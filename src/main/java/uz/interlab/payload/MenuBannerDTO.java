package uz.interlab.payload;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.menuBanner.MenuBanner;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MenuBannerDTO {

    Long id;

    boolean active;

    List<MenuBannerOptionDTO> menuOption;

    public MenuBannerDTO(MenuBanner menuBanner, String lang) {
        this.id = menuBanner.getId();

        this.menuOption = menuBanner.getMenuOption().stream()
                .map(option -> new MenuBannerOptionDTO(option, lang))
                .collect(Collectors.toList());
    }

}
