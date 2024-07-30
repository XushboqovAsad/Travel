package uz.market.backend.web.rest.product;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.market.backend.DTO.ProductDTO;
import uz.market.backend.domain.User;
import uz.market.backend.domain.product.Product;
import uz.market.backend.service.product.ProductService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api")
public class ProductController {

    private static final String UPLOADED_FOLDER = "C:\\Users\\asadb\\SuperProject\\clean-code\\uploads\\";

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ProductDTO addProduct(@RequestParam("name") @NotBlank String name,
                                 @RequestParam("description") @NotBlank String description,
                                 @RequestParam("price") @NotNull BigDecimal price,
                                 @RequestParam("image") MultipartFile image) {
        String imageUrl = null;
        if (!image.isEmpty()) {
            try {
                Path uploadPath = Paths.get(UPLOADED_FOLDER);
                if (Files.notExists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                byte[] bytes = image.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + image.getOriginalFilename());
                Files.write(path, bytes);
                imageUrl = "C:\\Users\\asadb\\SuperProject\\clean-code\\img\\" + image.getOriginalFilename();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to upload image", e);
            }
        }

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(name);
        productDTO.setDescription(description);
        productDTO.setPrice(price);
        productDTO.setImageUrl(imageUrl);

        return productService.addProduct(productDTO);
    }

    @PutMapping("/product/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ProductDTO updateProduct(@PathVariable Long id,
                                    @RequestParam("name") @NotBlank String name,
                                    @RequestParam("description") @NotBlank String description,
                                    @RequestParam("price") @NotNull BigDecimal price,
                                    @RequestParam(value = "image", required = false) MultipartFile image) {
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            try {
                byte[] bytes = image.getBytes();
                Path path = Paths.get(UPLOADED_FOLDER + image.getOriginalFilename());
                Files.write(path, bytes);
                imageUrl = "C:\\Users\\asadb\\SuperProject\\clean-code\\img\\" + image.getOriginalFilename();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to upload image");
            }
        }

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(id);
        productDTO.setName(name);
        productDTO.setDescription(description);
        productDTO.setPrice(price);
        if (imageUrl != null) {
            productDTO.setImageUrl(imageUrl);
        }

        return productService.updateProduct(productDTO);
    }
    @GetMapping("/product/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }
    @GetMapping("/product/all")
    public ResponseEntity getAllProducts() {
    return ResponseEntity.ok(productService.findAllProduct());
    }

    @DeleteMapping("/product/delete/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.ok("Product deleted successfully");
    }

}

