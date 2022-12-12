package example.skeleton.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsertUserResponse {
    private String userId;
    private String errorMessage;
    private boolean success;
}
