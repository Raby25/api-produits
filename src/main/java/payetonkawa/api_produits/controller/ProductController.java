package payetonkawa.api_produits.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import payetonkawa.api_produits.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {

    private ProductService service;

    public ProductController(ProductService service) {

        this.service = service;

    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("products", service.findAll());
        return "products"; // correspond à products.html dans templates/
    }

}
