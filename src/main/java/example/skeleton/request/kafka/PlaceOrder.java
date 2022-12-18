package example.skeleton.request.kafka;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceOrder {
    @NotNull
    private String id;
    @NotNull
    private String order;

}