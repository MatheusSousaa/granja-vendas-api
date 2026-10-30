package poultry_sales.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import poultry_sales.api.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
