package example.skeleton.constant;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Constants {
    public static final String WRONG_CREDENTIALS = "Wrong username or password";
    public static final String ENCRYPTION_ERROR = "Something wrong happens with encryption ...";

    public static Map<String, String> userTokens = new HashMap<>();
    public static Set<String> expiredUserTokens = new HashSet<>();

    private Constants() {
    }
}
