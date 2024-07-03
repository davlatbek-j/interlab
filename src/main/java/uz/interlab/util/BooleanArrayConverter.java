package uz.interlab.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class BooleanArrayConverter implements AttributeConverter<boolean[], String> {

    @Override
    public String convertToDatabaseColumn(boolean[] attribute) {
        if (attribute == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (boolean b : attribute) {
            sb.append(b ? "1" : "0").append(",");
        }
        // Remove the trailing comma
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    @Override
    public boolean[] convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return new boolean[0];
        }
        String[] data = dbData.split(",");
        boolean[] result = new boolean[data.length];
        for (int i = 0; i < data.length; i++) {
            result[i] = "1".equals(data[i]);
        }
        return result;
    }
}
