package uz.interlab.entity.doctor;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
public class DoctorHead
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String titleUz;
    String titleRu;

    String subTitleUz;
    String subTitleRu;

    @Column(length = 500)
    String descriptionUz;
    @Column(length = 500)
    String descriptionRu;

    String colourCode;

    String photoUrl;
}
