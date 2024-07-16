package uz.interlab.sms;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@lombok.Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Data
{
    String token;
}
