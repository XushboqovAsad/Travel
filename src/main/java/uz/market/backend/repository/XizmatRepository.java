package uz.market.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.market.backend.domain.Xizmat;

@Repository
public interface XizmatRepository extends JpaRepository<Xizmat, Long> {

}
