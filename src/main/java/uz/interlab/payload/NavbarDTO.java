package uz.interlab.payload;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.entity.navbar.Navbar;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NavbarDTO {

    Long id;

    boolean active;

    List<NavbarOptionDTO> navbarOptions;

    public NavbarDTO(Navbar navbar, String lang) {
        this.id = navbar.getId();

        this.navbarOptions = navbar.getNavbarOptions().stream()
                .map(option -> new NavbarOptionDTO(option, lang))
                .collect(Collectors.toList());
    }

}
