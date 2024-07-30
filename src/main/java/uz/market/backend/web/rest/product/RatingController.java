package uz.market.backend.web.rest.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.market.backend.domain.product.Rating;
import uz.market.backend.service.product.RatingService;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/{productId}")
    public Rating addRating(@PathVariable Long productId, @RequestParam int stars) {
        if (stars < 1 || stars > 5) {
            throw new IllegalArgumentException("Stars must be between 1 and 5");
        }
        return ratingService.addRating(productId, stars);
    }
}

