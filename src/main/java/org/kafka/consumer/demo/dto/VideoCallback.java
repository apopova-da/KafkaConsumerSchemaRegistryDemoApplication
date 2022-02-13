package org.kafka.consumer.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoCallback {

    @JsonProperty(value = "contact_info")
    public String contactInfo;

    @JsonProperty(value = "ehr_id")
    public String ehrId;

    @JsonProperty(value = "modality")
    public String modality;

    @JsonProperty("status")
    public String status;

    @Override
    public String toString() {
        return "VideoCallback{" +
            "contactInfo='" + contactInfo + '\'' +
            ", ehrId='" + ehrId + '\'' +
            ", modality='" + modality + '\'' +
            ", status='" + status + '\'' +
            '}';
    }
}
