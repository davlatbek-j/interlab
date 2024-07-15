package uz.interlab.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "profile_my_address")
public class ProfileMyAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String titleUz;

    String titleRu;

    @Column(length = 500)
    String addressInfoUz;

    @Column(length = 500)
    String addressInfoRu;

    boolean active;

}
