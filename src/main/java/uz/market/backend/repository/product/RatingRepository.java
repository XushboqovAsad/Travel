package uz.market.backend.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.market.backend.domain.product.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

}
