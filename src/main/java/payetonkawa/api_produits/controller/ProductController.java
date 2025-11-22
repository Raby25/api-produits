package payetonkawa.api_produits.controller;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{id}")
    public String getProductDetails(@PathVariable("id") Integer id, Model model) {
        Optional<Product> product = service.findById(id);
        if (!product.isPresent()) {
            return "product-not-found"; // créer une page si produit introuvable
        }
        model.addAttribute("product", product.get());
        return "product-details"; // templates/product-details.html
    }

    // Page formulaire modification
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model) {
        Optional<Product> product = service.findById(id);
        if (product.isEmpty()) {
            return "product-not-found";
        }
        model.addAttribute("product", product.get());
        return "edit-product"; // templates/edit-product.html
    }

    // Soumission du formulaire modification
    @PostMapping("/edit/{id}")
    public String updateProduct(@PathVariable("id") Integer id,
            @Valid @ModelAttribute("product") Product product,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            return "edit-product";
        }

        // Vérification nom unique sauf pour ce produit
        Optional<Product> existing = service.findByName(product.getName());
        if (existing.isPresent() && !existing.get().getId().equals(id)) {
            result.rejectValue("name", "error.product", "Un produit avec ce nom existe déjà !");
            return "edit-product";
        }

        product.setId(id); // important pour la mise à jour
        service.save(product);
        productProducer.envoyerProduit(product); 
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Integer id, Model model) {
        Optional<Product> product = service.findById(id);
        if (product.isPresent()) {
            service.delete(id); // supprime le produit
        } else {
            model.addAttribute("errorMessage", "Produit introuvable !");
            return "product-not-found"; // page d'erreur si produit inexistant
        }
        return "redirect:/products"; // retour à la liste
    }

}
