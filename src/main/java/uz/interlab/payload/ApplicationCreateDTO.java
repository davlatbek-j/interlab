package uz.interlab.payload;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicationCreateDTO {

    String fullName;

    String phoneNumber;

    String serviceName;

    String comment;

}
