package m2_suffix;

public class TextNormalizer {
    
    /**
     * Normalizes text by converting to lowercase, removing non-alphanumeric
     * punctuation characters, and collapsing whitespace.
     * Note: Since this physically alters the string length, indices returned
     * by suffix algorithms will refer to this NORMALIZED string.
     */
    public static String normalize(String text) {
        if (text == null) return "";
        
        StringBuilder sb = new StringBuilder();
        boolean lastWasSpace = true; // trims leading whitespace naturally
        
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            
            // Allow alphanumeric
            if (Character.isLetterOrDigit(c)) {
                sb.append(Character.toLowerCase(c));
                lastWasSpace = false;
            } 
            // Treat whitespace or punctuation as a space
            else if (Character.isWhitespace(c) || isPunctuation(c)) {
                if (!lastWasSpace) {
                    sb.append(' ');
                    lastWasSpace = true;
                }
            }
            // Ignore other symbols completely, or treat them as space depending on rule.
            // For this project, we treat standard punctuation as space to separate words.
        }
        
        String result = sb.toString();
        // Remove trailing space if it exists
        if (result.length() > 0 && result.charAt(result.length() - 1) == ' ') {
            result = result.substring(0, result.length() - 1);
        }
        
        return result;
    }
    
    private static boolean isPunctuation(char c) {
        return c == '.' || c == ',' || c == '!' || c == '?' || c == ';' || c == ':' || 
               c == '-' || c == '_' || c == '"' || c == '\'' || c == '(' || c == ')' || 
               c == '[' || c == ']' || c == '{' || c == '}';
    }
}
