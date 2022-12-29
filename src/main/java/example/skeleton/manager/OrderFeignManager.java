package example.skeleton.manager;

import example.skeleton.model.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderFeignManager {
    @Autowired
    private OrderFeignClient orderFeignClient;

    public List<OrderEntity> getUserOrders(Long userId) {
        return orderFeignClient.getUserOrders(userId);
    }

    public OrderEntity getOrder(Long orderId) {
        return orderFeignClient.getOrder(orderId);
    }

    public OrderEntity getUserOrder(Long userId, Long orderId) {
        return orderFeignClient.getUserOrder(userId, orderId);
    }
}
