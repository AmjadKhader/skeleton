package example.skeleton.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity implements Serializable {
    private Long orderId;
    private String orderDescription;
    private Long userId;
}