package uz.interlab.entity.form.onlineRegstration;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
public class OnlineRegistrationRequest
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String doctorSlug;

    @ElementCollection
    List<String> answer;

    @Temporal(TemporalType.TIMESTAMP)
    Date createdAt;

    @PrePersist
    protected void onCreate()
    {
        createdAt = new Date();
    }
}
