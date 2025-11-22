package payetonkawa.api_produits.TestProductConsumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

import payetonkawa.api_produits.model.Product;

public class TestProductConsumer {
    @RabbitListener(queues = "produits.queue")
    public void receive(Product product) {
        System.out.println("=== Message reçu dans RabbitMQ ===");
        System.out.println("Nom du produit : " + product.getName());
        System.out.println("Description : " + product.getDescription());
        System.out.println("Prix : " + product.getPrice());
        System.out.println("Stock : " + product.getStock());
        System.out.println("Date de création : " + product.getCreatedAt());
        System.out.println("=================================");
    }
}
