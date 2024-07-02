package uz.interlab.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
@Table(name = "photo")
public class Photo
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    String filepath;

    String httpUrl;

    String type;

    public Photo(String name, String filepath, String httpUrl)
    {
        this.name = name;
        this.filepath = filepath;
        this.httpUrl = httpUrl;
    }
}
