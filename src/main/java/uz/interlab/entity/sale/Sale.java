package uz.interlab.entity.sale;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
public class Sale
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true)
    String slug;

    String nameUz;
    String nameRu;

    String timeUz;
    String timeRu;

    String mainPhotoUz;
    String mainPhotoRu;

    @OneToOne(cascade = CascadeType.ALL)
    SaleDetails details;

    boolean main;
    boolean active;
}
