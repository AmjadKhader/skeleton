package example.skeleton.request;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class UserLogoutRequest {
    @NotNull
    private String username;
}
