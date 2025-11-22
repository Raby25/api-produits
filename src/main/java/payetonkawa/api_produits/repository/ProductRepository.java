package payetonkawa.api_produits.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import payetonkawa.api_produits.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByName(String name);

    boolean existsByName(String name);
}
