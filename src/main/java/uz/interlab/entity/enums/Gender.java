package uz.interlab.entity.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import uz.interlab.util.GenderDeserializer;

@JsonDeserialize(using = GenderDeserializer.class)
public enum Gender
{
    MALE,
    FEMALE
}
