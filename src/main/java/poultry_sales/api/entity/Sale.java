package poultry_sales.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate saleDate;

    @Enumerated(EnumType.STRING)
    private SaleStatus status;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<SaleItem> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client sellerClient;

    public void addItem(SaleItem item) {
        this.items.add(item);
        item.setSale(this);
    }

    public BigDecimal getTotal() {
        BigDecimal total =  BigDecimal.ZERO;
        for (SaleItem item : items) {
            BigDecimal subtotal = item.getQuantity().multiply(item.getPrice());
            total = total.add(subtotal);
        }
        return total;
    }
}
