package payetonkawa.api_produits.dto;

import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto implements Serializable {
    private int id;
    private String name;
    private String description;
    private float price;
    private int stock;
    private Date createdAt;
}
