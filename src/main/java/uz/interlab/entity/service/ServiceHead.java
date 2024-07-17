package uz.interlab.entity.service;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
public class ServiceHead
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String titleUz;
    String titleRu;

    String subTitleUz;
    String subTitleRu;

    String descriptionUz;
    String descriptionRu;

    @ElementCollection
    List<String> gallery;
}