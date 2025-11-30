package payetonkawa.api_produits.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

import payetonkawa.api_produits.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductConsumer {
    @RabbitListener(queues = "${rabbitmq.product.queue}")
    public void receive(Product product) {
        System.out.println("Produit reçu depuis RabbitMQ : " + product);
    }

}
