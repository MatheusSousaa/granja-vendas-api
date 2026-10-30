package poultry_sales.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import poultry_sales.api.model.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByQuantityLessThan(BigDecimal quantity);
    List<Product> findByExpirationDateLessThanEqual(LocalDate date);
}
