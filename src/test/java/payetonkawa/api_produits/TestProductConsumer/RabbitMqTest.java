/*
 * package payetonkawa.api_produits.TestProductConsumer;
 * 
 * import java.util.Date;
 * 
 * import org.springframework.amqp.rabbit.annotation.RabbitListener;
 * import org.springframework.stereotype.Component;
 * 
 * import payetonkawa.api_produits.config.ProductProducer;
 * import payetonkawa.api_produits.model.Product;
 * 
 * import org.junit.jupiter.api.Test;
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.boot.test.context.SpringBootTest;
 * 
 * @SpringBootTest
 * public class RabbitMqTest {
 * 
 * 
 * @Autowired
 * private ProductProducer productProducer;
 * 
 * @Test
 * public void testReceiveProduit() throws InterruptedException {
 * Product p = new Product();
 * p.setName("TestProduct");
 * p.setDescription("Produit test");
 * p.setPrice(10.5f);
 * p.setStock(5);
 * p.setCreatedAt(new Date());
 * 
 * // Envoyer le message
 * productProducer.envoyerProduit(p);
 * 
 * // Attendre 1-2 secondes pour que le consumer reçoive le message
 * Thread.sleep(2000);
 * }
 * 
 * }
 */