package org.kafka.consumer.demo.configuration;

import io.confluent.kafka.serializers.json.JsonSchemaAndValue;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.CommonLoggingErrorHandler;
import org.springframework.kafka.listener.ContainerProperties;

import static io.confluent.kafka.serializers.AbstractKafkaSchemaSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG;
import static io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializerConfig.FAIL_INVALID_SCHEMA;

@EnableKafka
@Configuration
public class KafkaConsumerConfiguration {

    @Autowired
    private KafkaProperties properties;

    @Bean
    public CommonLoggingErrorHandler errorHandler() {
        return new CommonLoggingErrorHandler();
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, JsonSchemaAndValue> kafkaListenerContainerFactory() {
        var props = properties.buildConsumerProperties();
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomKafkaJsonSchemaDeserializer.class);
        props.put(SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        props.put(FAIL_INVALID_SCHEMA, true);

        var containerFactory = new ConcurrentKafkaListenerContainerFactory<String, JsonSchemaAndValue>();
        containerFactory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(props));
        containerFactory.setCommonErrorHandler(errorHandler());
        containerFactory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);

        return containerFactory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> schemaUpdateListenerContainerFactory() {
        var props = properties.buildConsumerProperties();
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        var containerFactory = new ConcurrentKafkaListenerContainerFactory<String, String>();
        containerFactory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(props));
        containerFactory.setCommonErrorHandler(errorHandler());
        containerFactory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);

        return containerFactory;
    }
}
