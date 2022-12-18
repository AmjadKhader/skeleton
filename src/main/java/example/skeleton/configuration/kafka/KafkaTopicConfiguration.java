package example.skeleton.configuration.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

import static example.skeleton.constant.KafkaConstants.DELETE_TOPIC;
import static example.skeleton.constant.KafkaConstants.POST_TOPIC;
import static example.skeleton.constant.KafkaConstants.PUT_TOPIC;

@Configuration
public class KafkaTopicConfiguration {
    @Value(value = "localhost:9092")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic createPostTopic() {
        return new NewTopic(POST_TOPIC, 1, (short) 1);
    }

    @Bean
    public NewTopic createPutTopic() {
        return new NewTopic(PUT_TOPIC, 1, (short) 1);
    }

    @Bean
    public NewTopic createDeleteTopic() {
        return new NewTopic(DELETE_TOPIC, 1, (short) 1);
    }
}
