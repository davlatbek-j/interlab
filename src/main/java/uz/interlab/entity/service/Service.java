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

    @Column(unique = true)
    String slug;

    String nameUz;

    String nameRu;

    String descriptionUz;

    String descriptionRu;

    String iconUrl;

    String categoryUz;

    String categoryRu;

    @JsonIgnore
    Long detailsId;

    boolean active;

    boolean main;
}
