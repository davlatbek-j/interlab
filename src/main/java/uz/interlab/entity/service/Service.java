package uz.interlab.entity.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
@Table(name = "service")
public class Service
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String nameUz;

    String nameRu;

    String descriptionUz;

    String descriptionRu;

    String iconUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    ServiceDetails details;

    boolean active;
}
