package uz.market.backend.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.market.backend.DTO.ProductDTO;
import uz.market.backend.domain.product.Product;
import uz.market.backend.repository.product.ProductRepository;
import uz.market.backend.security.ResourceNotFoundException;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductDTO addProduct(ProductDTO productDTO) {
        // Product creation logic
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setImageUrl(productDTO.getImageUrl());

        productRepository.save(product);

        productDTO.setId(product.getId());
        return productDTO;
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        return new ProductDTO(product);
    }

    public ProductDTO updateProduct(ProductDTO productDTO) {
        Product product = productRepository.findById(productDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + productDTO.getId()));

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());

        if (productDTO.getImageUrl() != null) {
            product.setImageUrl(productDTO.getImageUrl());
        }

        productRepository.save(product);
        return new ProductDTO(product);
    }
    public List<Product> findAllProduct(){
        return productRepository.findAll();
    }

    public void delete(Long id){
        productRepository.deleteById(id);
    }
}




