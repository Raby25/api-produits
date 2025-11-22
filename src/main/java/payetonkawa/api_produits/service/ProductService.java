package payetonkawa.api_produits.service;

import org.springframework.stereotype.Service;

import payetonkawa.api_produits.model.Product;
import payetonkawa.api_produits.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> findAll() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Optional<Product> findById(Integer id) {
        return repository.findById(id);
    }

    public Product save(Product product) {
        return repository.save(product);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    public Optional<Product> findByName(String name) {
        return repository.findByName(name);
    }
}
