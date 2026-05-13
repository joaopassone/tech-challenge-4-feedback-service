package br.com.fiap.tc4.services;

import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import com.google.pubsub.v1.TopicName;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PubSubService {

    @ConfigProperty(name = "quarkus.google.cloud.project-id")
    String projectId;

    public void publish(String topicId, String messagePayload) {
        TopicName topicName = TopicName.of(projectId, topicId);
        Publisher publisher = null;

        try {
            // Cria o publicador para o tópico específico
            publisher = Publisher.newBuilder(topicName).build();
            
            // Converte a String (ID do feedback) em bytes para o formato do Pub/Sub
            ByteString data = ByteString.copyFromUtf8(messagePayload);
            PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(data).build();
            
            // Envia a mensagem de forma síncrona
            publisher.publish(pubsubMessage).get();
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao publicar mensagem no Pub/Sub: " + e.getMessage(), e);
        } finally {
            if (publisher != null) {
                publisher.shutdown();
            }
        }
    }
}

