package example.skeleton.service;

import example.skeleton.manager.OrderFeignManager;
import example.skeleton.model.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderFeignManager orderFeignManager;

    public List<OrderEntity> getUserOrders(Long userId) {
        return orderFeignManager.getUserOrders(userId);
    }

    public OrderEntity getOrder(Long orderId) {
        return orderFeignManager.getOrder(orderId);
    }

    public OrderEntity getUserOrder(Long userId, Long orderId) {
        return orderFeignManager.getUserOrder(userId, orderId);
    }
}