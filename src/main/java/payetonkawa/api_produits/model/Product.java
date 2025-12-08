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

}