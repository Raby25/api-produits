package payetonkawa.api_produits.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import payetonkawa.api_produits.model.Product;
import payetonkawa.api_produits.service.ProductService;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public Product create(@Valid @RequestBody Product product) {
        return service.create(product);
    }

    @GetMapping
    public List<Product> all() {
        return service.all();
    }

    @GetMapping("/{id}")
    public Product get(@PathVariable int id) {
        return service.get(id);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable int id, @RequestBody Product product) {
        return service.update(id, product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}
