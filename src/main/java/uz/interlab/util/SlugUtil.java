package uz.interlab.util;

import org.springframework.stereotype.Component;

@Component
public class SlugUtil {

    public static String makeSlug(String name) {
        if (name == null) {
            return "";
        }
        name = name.trim();
        return name.replaceAll("\\s+", "-").toLowerCase();
    }
}
