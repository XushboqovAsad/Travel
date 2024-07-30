package uz.market.backend.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.market.backend.domain.product.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
