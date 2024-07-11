package uz.interlab.entity.instruction;

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

@Entity
public class InstructionHead
{
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    Long id;

    String titleUz;
    String titleRu;

    String descriptionUz;
    String descriptionRu;

    String colourCode;

    String iconUrl;
}
