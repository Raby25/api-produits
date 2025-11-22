package payetonkawa.api_produits.config;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import org.springframework.beans.factory.annotation.Value;
import payetonkawa.api_produits.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductProducer {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.product.queue}")
    private String queueName;

    public ProductProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void envoyerProduit(Product product) {
        rabbitTemplate.convertAndSend(queueName, product);
        System.out.println("Message envoyé à RabbitMQ : " + product);
    }

}
