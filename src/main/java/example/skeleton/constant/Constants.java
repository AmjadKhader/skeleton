package example.skeleton.constant;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final String WRONG_CREDENTIALS = "Wrong username or password";
    public static final String ENCRYPTION_ERROR = "Something wrong happens with encryption ...";

    public static Map<String, String> userToken = new HashMap<>();

    private Constants() {
    }
}
