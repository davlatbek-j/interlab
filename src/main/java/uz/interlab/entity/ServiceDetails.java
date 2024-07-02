package uz.interlab.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
@Table(name = "service_details")
public class ServiceDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String nameUz;
    String nameRu;

    String descriptionUz;
    String descriptionRu;

    @Column(length = 2000)
    String textUz;

    @Column(length = 2000)
    String textRu;

    String photoUrl;
}
