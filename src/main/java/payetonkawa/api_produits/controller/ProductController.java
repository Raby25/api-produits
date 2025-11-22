package payetonkawa.api_produits.controller;


import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import payetonkawa.api_produits.config.ProductProducer;
import payetonkawa.api_produits.model.Product;
import payetonkawa.api_produits.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;
    private final ProductProducer productProducer;

    public ProductController(ProductService service, ProductProducer productProducer) {

        this.service = service;
        this.productProducer = productProducer;

    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("products", service.findAll());
        return "products"; // correspond à products.html dans templates/
    }

    // Page formulaire ajout
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        return "add-product"; // templates/add-product.html
    }

    @PostMapping("/add")
    public String createProduct(
            @Valid @ModelAttribute("product") Product product,
            BindingResult result,
            Model model) {

        // erreurs de validation (champs vides, formats invalides…)
        if (result.hasErrors()) {
            return "add-product";
        }

        // Vérification logique : le nom existe déjà (via le service)
        if (service.existsByName(product.getName())) {
            result.rejectValue("name", "error.product", "Un produit avec ce nom existe déjà !");
            return "add-product";
        }

        // Protection finale : contrainte UNIQUE de la base de données
        try {
            service.save(product);
        } catch (DataIntegrityViolationException e) {
            // Si quelqu’un essaie de contourner, ou période de concurrence...
            result.rejectValue("name", "error.product", "Ce nom existe déjà (contrainte BDD)");
            return "add-product";
        }

        productProducer.envoyerProduit(product);
        // OK → redirection vers la liste
        return "redirect:/products";
    }

}
