package poultry_sales.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poultry_sales.api.model.Product;
import poultry_sales.api.repositories.ProductRepository;
import poultry_sales.api.services.exceptions.ResourceNotFoundException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<Product> findLowStock(BigDecimal limit) {
        return productRepository.findByQuantityLessThan(limit);
    }

    @Transactional(readOnly = true)
    public List<Product> findExpiringInDays(int days) {
        LocalDate limit = LocalDate.now().plusDays(days);
        return productRepository.findByExpirationDateLessThanEqual(limit);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Produto não encontrado. ID: " + id
                ));
    }

    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product update(Long id, Product product) {
        Product up = findById(id);
        up.setName(product.getName());
        up.setPrice(product.getPrice());
        up.setQuantity(product.getQuantity());
        up.setExpirationDate(product.getExpirationDate());
        up.setType(product.getType());
        return up;
    }

    @Transactional
    public void deleteById(Long id) {
        Product product = findById(id);
        productRepository.delete(product);
    }
}