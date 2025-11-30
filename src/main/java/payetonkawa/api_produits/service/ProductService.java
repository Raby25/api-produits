package payetonkawa.api_produits.service;

import java.util.List;
import java.util.Date;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import payetonkawa.api_produits.model.Product;
import payetonkawa.api_produits.repository.ProductRepository;
import payetonkawa.api_produits.dto.ProductDto;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final RabbitTemplate rabbitTemplate;

    public Product create(Product p) {
        // Initialiser la date de création si besoin
        p.setCreatedAt(new Date());

        Product saved = repository.save(p);

        ProductDto dto = new ProductDto(
                saved.getId(),
                saved.getName(),
                saved.getDescription(),
                saved.getPrice(),
                saved.getStock(),
                saved.getCreatedAt());

        rabbitTemplate.convertAndSend("products.exchange", (String) "product.created", dto,
                (org.springframework.amqp.core.MessagePostProcessor) message -> message);

        return saved;
    }

    public Product update(int id, Product p) {
        Product existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existing.setName(p.getName());
        existing.setDescription(p.getDescription());
        existing.setPrice(p.getPrice());
        existing.setStock(p.getStock());

        Product updated = repository.save(existing);

        ProductDto dto = new ProductDto(
                updated.getId(),
                updated.getName(),
                updated.getDescription(),
                updated.getPrice(),
                updated.getStock(),
                updated.getCreatedAt());

        rabbitTemplate.convertAndSend("products.exchange", (String) "product.updated", dto,
                (org.springframework.amqp.core.MessagePostProcessor) message -> message);

        return updated;
    }

    public void delete(int id) {
        Product existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        repository.deleteById(id);

        ProductDto dto = new ProductDto(
                existing.getId(),
                existing.getName(),
                existing.getDescription(),
                existing.getPrice(),
                existing.getStock(),
                existing.getCreatedAt());

        rabbitTemplate.convertAndSend("products.exchange", (String) "product.deleted", dto,
                (org.springframework.amqp.core.MessagePostProcessor) message -> message);
    }

    public List<Product> all() {
        return repository.findAll();
    }

    public Product get(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product findByName(String name) {
        return repository.findByName(name);
    }
}
