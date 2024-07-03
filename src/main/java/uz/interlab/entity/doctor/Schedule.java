package uz.interlab.entity.doctor;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.util.BooleanArrayConverter;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
@Table(name = "doctor_schedule")
public class Schedule
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Convert(converter = BooleanArrayConverter.class)
    boolean[] weak;

    String fromTime;

    String toTime;
}
