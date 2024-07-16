package uz.interlab.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity(name = "address")
public class Address
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String nameUz;

    String nameRu;

    String locationUz;

    String locationRu;

    String url;

    @ElementCollection
    List<String> workingTimeUz;

    @ElementCollection
    List<String> workingTimeRu;

    @Column(unique = true)
    String slug;

    boolean active;

    boolean main;
}
