package uz.market.backend.service.product;

import org.springframework.stereotype.Service;
import uz.market.backend.domain.product.Product;
import uz.market.backend.domain.product.Rating;
import uz.market.backend.repository.product.ProductRepository;
import uz.market.backend.repository.product.RatingRepository;

import java.util.OptionalDouble;

@Service
public class RatingService {


    private final RatingRepository ratingRepository;

    private final ProductRepository productRepository;

    public RatingService(RatingRepository ratingRepository, ProductRepository productRepository) {
        this.ratingRepository = ratingRepository;
        this.productRepository = productRepository;
    }

    public Rating addRating(Long productId, int stars) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Rating rating = new Rating();
        rating.setProduct(product);
        rating.setStars(stars);

        return ratingRepository.save(rating);
    }

    public double getAverageRating(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        OptionalDouble average = product.getRatings().stream()
                .mapToInt(Rating::getStars)
                .average();

        return average.isPresent() ? average.getAsDouble() : 0.0;
    }
}


