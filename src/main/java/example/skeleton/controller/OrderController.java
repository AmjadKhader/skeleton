package example.skeleton.controller;

import example.skeleton.manager.KafkaManager;
import example.skeleton.request.PlaceOrderRequest;
import example.skeleton.response.OrderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    KafkaManager kafkaManager;

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
}
