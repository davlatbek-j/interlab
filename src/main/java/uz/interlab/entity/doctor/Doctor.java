package uz.interlab.entity.doctor;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "doctor")
public class Doctor
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String fullNameUz;
    String fullNameRu;

    @ElementCollection
    List<String> specialityUz;

    @ElementCollection
    List<String> specialityRu;

    String photoUrl;

    boolean main;

    boolean active;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    DoctorDetails details;
}
