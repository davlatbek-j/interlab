package uz.interlab.entity.menuBanner;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity(name = "menu_banner")
public class MenuBanner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    boolean active;

    @OneToMany(cascade = CascadeType.ALL)
    List<MenuBannerOption> menuOption;

}
