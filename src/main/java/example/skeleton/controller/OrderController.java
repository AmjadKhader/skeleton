package example.skeleton.controller;

import example.skeleton.manager.KafkaManager;
import example.skeleton.model.OrderEntity;
import example.skeleton.request.PlaceOrderRequest;
import example.skeleton.response.OrderResponse;
import example.skeleton.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    KafkaManager kafkaManager;

    @Autowired
    OrderService orderService;

    @PostMapping(value = "/{user_id}", produces = "application/json")
    public ResponseEntity<?> placeOrder(@PathVariable("user_id") String userId,
                                        @RequestBody PlaceOrderRequest placeOrderRequest) {
        try {
            kafkaManager.sendPostMessage(userId, placeOrderRequest.getOrder());
            return new ResponseEntity<>(new OrderResponse("", true), HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(new OrderResponse(exception.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{order_id}", produces = "application/json")
    public ResponseEntity<?> updateOrderDescription(@PathVariable("order_id") String orderId,
                                                    @RequestBody PlaceOrderRequest placeOrderRequest) {
        try {
            kafkaManager.sendPutMessage(orderId, placeOrderRequest.getOrder());
            return new ResponseEntity<>(new OrderResponse("", true), HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(new OrderResponse(exception.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/{order_id}", produces = "application/json")
    public ResponseEntity<?> deleteOrder(@PathVariable("order_id") String orderId) {
        try {
            kafkaManager.sendDeleteMessage(orderId);
            return new ResponseEntity<>(new OrderResponse("", true), HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(new OrderResponse(exception.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/{order_id}", produces = "application/json")
    public ResponseEntity<?> getOrder(@PathVariable("order_id") String orderId) {
        try {
            OrderEntity order = orderService.getOrder(Long.valueOf(orderId));
            if (Objects.nonNull(order)) {
                return new ResponseEntity<>(order, HttpStatus.OK);
            }
            return new ResponseEntity<>(new OrderResponse("Order was not found", false), HttpStatus.NOT_FOUND);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(new OrderResponse(exception.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/user/{user_id}", produces = "application/json")
    public ResponseEntity<?> getUserOrders(@PathVariable("user_id") String userId) {
        try {
            List<OrderEntity> orderList = orderService.getUserOrders(Long.valueOf(userId));
            if (!orderList.isEmpty()) {
                return new ResponseEntity<>(orderList, HttpStatus.OK);
            }
            return new ResponseEntity<>(new OrderResponse("User " + userId + " has no orders", false), HttpStatus.NOT_FOUND);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(new OrderResponse(exception.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{user_id}/{order_id}", produces = "application/json")
    public ResponseEntity<?> getUserOrders(@PathVariable("user_id") String userId, @PathVariable("order_id") String orderId) {
        try {
            OrderEntity order = orderService.getUserOrder(Long.valueOf(userId), Long.valueOf(orderId));
            if (Objects.nonNull(order)) {
                return new ResponseEntity<>(order, HttpStatus.OK);
            }
            return new ResponseEntity<>(new OrderResponse("Order was not found", false), HttpStatus.NOT_FOUND);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(new OrderResponse(exception.getMessage(), false), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
