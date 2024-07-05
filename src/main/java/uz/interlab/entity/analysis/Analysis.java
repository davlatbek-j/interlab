package uz.interlab.entity.analysis;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "analysis")
public class Analysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String titleUz;

    String titleRu;

    String category;

    boolean active;

    @OneToMany(cascade = CascadeType.ALL)
    List<AnalysisOption> analysisOptions;

}
