package uz.interlab.entity.analysis;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "analysis_option")
public class AnalysisOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String titleUz;

    String titleRu;

    @Column(length = 3000)
    String descriptionUz;

    @Column(length = 3000)
    String descriptionRu;

    Double price;

    @Column(unique = true)
    String slug;

    boolean popular;

}
