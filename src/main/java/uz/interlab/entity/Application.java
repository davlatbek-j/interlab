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
@Entity(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    List<String> usersInfo;

}
