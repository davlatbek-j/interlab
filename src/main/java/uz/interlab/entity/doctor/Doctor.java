package uz.interlab.entity.doctor;

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
@Table(name = "doctor")
public class Doctor
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String fullNameUz;
    String fullNameRu;

    String specialityUz;
    String specialityRu;

    String photoUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    DoctorDetails details;

    boolean active;
}
