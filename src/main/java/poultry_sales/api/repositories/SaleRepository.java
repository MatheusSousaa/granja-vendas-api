package poultry_sales.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import poultry_sales.api.model.Sale;
import poultry_sales.api.model.enums.SaleStatus;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByStatus(SaleStatus quantity);
}
