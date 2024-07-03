package uz.interlab.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.interlab.payload.ApplicationCreateDTO;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String fullName;

    String phoneNumber;

    String serviceName;

    @Column(length = 1000)
    String comment;

    public static Application map(ApplicationCreateDTO createDTO){
        Application application=new Application();
        application.setFullName(createDTO.getFullName());
        application.setPhoneNumber(createDTO.getPhoneNumber());
        application.setServiceName(createDTO.getServiceName());
        application.setComment(createDTO.getComment());
        return application;
    }

}
