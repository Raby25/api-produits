package payetonkawa.api_produits.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import payetonkawa.api_produits.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByName(String name);

    boolean existsByName(String name);
}
