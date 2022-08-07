package seller.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import seller.entity.Seller;

@Repository
public interface SellerRepo extends JpaRepository<Seller,Long> {
}
