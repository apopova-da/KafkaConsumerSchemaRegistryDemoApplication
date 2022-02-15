package org.kafka.consumer.demo;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class VideoCallbackEventListener {

    private static final Logger logger = LoggerFactory.getLogger(VideoCallbackEventListener.class);

    @KafkaListener(
        topics = "${kafka.poc-events.topic-name}",
        containerFactory = "kafkaListenerContainerFactory"
    )
    public void onEvent(ConsumerRecord<String, JsonNode> record, Acknowledgment acknowledgment) {
        logger.info(String.format("Received a new message: key=[%s], value=[%s]", record.key(), record.value()));
        acknowledgment.acknowledge();
    }
}
