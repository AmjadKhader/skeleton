package example.skeleton.manager;

import example.skeleton.model.OrderEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Component
@FeignClient(value = "orderClient", url = "${feign-client.order.baseUrl}")
public interface OrderFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/order/user/{user_id}", produces = "application/json")
    List<OrderEntity> getUserOrders(@PathVariable("user_id") Long userId);

    @RequestMapping(method = RequestMethod.GET, value = "/order/{order_id}", produces = "application/json")
    OrderEntity getOrder(@PathVariable("order_id") Long orderId);

    @RequestMapping(method = RequestMethod.GET, value = "/order/{user_id}/{order_id}", produces = "application/json")
    OrderEntity getUserOrder(@PathVariable("user_id") Long userId, @PathVariable("order_id") Long orderId);
}