package example.skeleton.manager;

import com.google.gson.Gson;
import example.skeleton.request.kafka.PlaceOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static example.skeleton.constant.KafkaConstants.DELETE_TOPIC;
import static example.skeleton.constant.KafkaConstants.POST_TOPIC;
import static example.skeleton.constant.KafkaConstants.PUT_TOPIC;

@Service
public class KafkaManager {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendPostMessage(String id, String order) {
        kafkaTemplate.send(POST_TOPIC, new Gson().toJson(new PlaceOrder(id, order)));
    }

    public void sendPutMessage(String id, String order) {
        kafkaTemplate.send(PUT_TOPIC, new Gson().toJson(new PlaceOrder(id, order)));
    }

    public void sendDeleteMessage(String id) {
        kafkaTemplate.send(DELETE_TOPIC, id);
    }
}