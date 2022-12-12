package example.skeleton.request;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class UserLoginRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
