package uz.interlab.entity.legal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "legal_page_advantage")
public class LegalPageAdvantage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String bodyUz;

    String bodyRu;

    boolean active;

}
