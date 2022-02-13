package org.kafka.consumer.demo.configuration;

import io.confluent.kafka.serializers.KafkaJsonDeserializerConfig;
import org.kafka.consumer.demo.dto.VideoCallback;
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
    public ConcurrentKafkaListenerContainerFactory<String, VideoCallback> kafkaListenerContainerFactory() {
        var props = properties.buildConsumerProperties();
        props.put(SCHEMA_REGISTRY_URL_CONFIG, "http://localhost:8081");
        props.put(KafkaJsonDeserializerConfig.JSON_VALUE_TYPE, VideoCallback.class.getName());

        var containerFactory = new ConcurrentKafkaListenerContainerFactory<String, VideoCallback>();
        containerFactory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(props));
        containerFactory.setCommonErrorHandler(errorHandler());
        containerFactory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);

        return containerFactory;
    }
}
