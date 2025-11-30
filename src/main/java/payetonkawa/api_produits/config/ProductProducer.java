package payetonkawa.api_produits.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import payetonkawa.api_produits.model.Product;

@Component
public class ProductProducer implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final AmqpAdmin amqpAdmin;

    @Value("${rabbitmq.product.exchange}")
    private String exchange;

    @Value("${rabbitmq.product.routing-key}")
    private String routingKey;

    @Value("${rabbitmq.product.queue}")
    private String queueName;

    public ProductProducer(RabbitTemplate rabbitTemplate, AmqpAdmin amqpAdmin) {
        this.rabbitTemplate = rabbitTemplate;
        this.amqpAdmin = amqpAdmin;
    }

    @Override
    public void run(String... args) throws Exception {
        // Attendre que la queue soit prête
        while (amqpAdmin.getQueueProperties(queueName) == null) {
            System.out.println("Waiting for RabbitMQ queue to be ready...");
            Thread.sleep(1000);
        }

        // Exemple de produit à envoyer au démarrage
        Product product = new Product();
        product.setName("Produit Exemple");
        product.setDescription("Description du produit");
        product.setPrice(99.99f);
        product.setStock(50);

        rabbitTemplate.convertAndSend(exchange, routingKey, product);
        System.out.println("Message envoyé !");

    }

    /*
     * public void envoyerProduit(Product product) {
     * // Envoi du message via l'exchange et la routing key
     * rabbitTemplate.convertAndSend(exchange, routingKey, product,
     * message -> message); // Utilisation d'un MessagePostProcessor neutre
     * System.out.println("Message envoyé à RabbitMQ : " + product);
     * }
     */
}
