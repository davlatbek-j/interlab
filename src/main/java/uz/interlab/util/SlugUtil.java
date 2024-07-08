package uz.interlab.util;

public class SlugUtil {

    public static String makeSlug(String name) {
        if (name == null) {
            return "";
        }

        // Trim the outermost spaces
        name = name.trim();

        // Remove unwanted characters and keep only letters, digits, and spaces
        name = name.replaceAll("[^\\w\\s]", "");

        // Replace spaces between words with hyphens and convert to lowercase
        return name.replaceAll("\\s+", "-").toLowerCase();
    }
}
