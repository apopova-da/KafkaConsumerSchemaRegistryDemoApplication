package org.kafka.consumer.demo.configuration;

import io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializer;
import io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializerConfig;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class CustomKafkaJsonSchemaDeserializer<T>
    extends KafkaJsonSchemaDeserializer<T>
    implements Deserializer<T> {

    /**
     * Constructor used by Kafka consumer.
     * */
    public CustomKafkaJsonSchemaDeserializer() {
    }

    @Override
    public void configure(Map<String, ?> props, boolean isKey) {
        configure(new KafkaJsonSchemaDeserializerConfig(props), isKey);
    }

    @SuppressWarnings("unchecked")
    @Override
    public T deserialize(String topic, byte[] data) {
        return (T) deserialize(true, topic, false, data);
    }

    @Override
    public void close() {

    }
}
