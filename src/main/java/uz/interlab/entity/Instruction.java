package uz.interlab.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity(name = "instruction")
public class Instruction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String nameUz;

    String nameRu;

    boolean active;

    @OneToMany(cascade = CascadeType.ALL)
    List<InstructionOption> options;
}
