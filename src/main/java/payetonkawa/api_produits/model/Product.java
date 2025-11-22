package payetonkawa.api_produits.model;

import java.util.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "products")
@Data // getters + setters + toString
@NoArgsConstructor // constructeur vide
@AllArgsConstructor // constructeur avec arguments
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Le nom est obligatoire")
    @Column(unique = true)
    private String name;
    private String description;
    private float price;
    private int stock;
    private Date createdAt;

    // Getters

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    // Setters

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
}