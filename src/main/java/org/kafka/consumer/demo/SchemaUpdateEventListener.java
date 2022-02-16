package org.kafka.consumer.demo;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class SchemaUpdateEventListener {

    private static final Logger logger = LoggerFactory.getLogger(SchemaUpdateEventListener.class);

    @KafkaListener(
        topics = "${kafka.schema-update-events.topic-name}",
        containerFactory = "schemaUpdateListenerContainerFactory"
    )
    public void onEvent(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        logger.info(
            String.format(
                "Received a new message: key=[%s], value=[%s]",
                record.key(),
                record.value()
            )
        );
        acknowledgment.acknowledge();
    }
}
