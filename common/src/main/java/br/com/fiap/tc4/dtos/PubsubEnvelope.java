package br.com.fiap.tc4.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PubsubEnvelope {
     public Message message;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Message {
        public String data;
        public String messageId;
    }
}
