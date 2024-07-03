package uz.interlab.entity.footer;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
public class Footer
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String logoUrl;

    String telegramUrl;

    String instagramUrl;
    String youtubeUrl;
    String facebookUrl;

    @OneToMany(cascade = CascadeType.ALL)
    List<FooterOption> footerOption;

    @OneToOne(cascade = CascadeType.ALL)
    Creator creator;

    boolean active;
}
