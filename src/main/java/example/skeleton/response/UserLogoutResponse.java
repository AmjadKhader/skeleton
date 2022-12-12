package example.skeleton.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLogoutResponse {
    private String errorMessage;
    private boolean success;
}
