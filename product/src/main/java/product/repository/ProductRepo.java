package product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import product.entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {


    Page<Product> findAllBySellerIdAndActiveTrue(Long sellerId, Pageable pageable);
}
