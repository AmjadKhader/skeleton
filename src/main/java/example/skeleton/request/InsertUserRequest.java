package example.skeleton.request;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class InsertUserRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
