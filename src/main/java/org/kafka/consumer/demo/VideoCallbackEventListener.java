package org.kafka.consumer.demo;

import io.confluent.kafka.serializers.json.JsonSchemaAndValue;
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
    public void onEvent(ConsumerRecord<String, JsonSchemaAndValue> record, Acknowledgment acknowledgment) {
        logger.info(
            String.format(
                "Received a new message: key=[%s], value=[%s], schema version = [%s], schema=[%s]",
                record.key(),
                record.value().getValue(),
                record.value().getSchema().version(),
                record.value().getSchema()
            )
        );
        acknowledgment.acknowledge();
    }
}
