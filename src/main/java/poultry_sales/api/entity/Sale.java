package poultry_sales.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import poultry_sales.api.entity.enums.SaleStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    private LocalDate saleDate = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private SaleStatus status = SaleStatus.PENDING;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<SaleItem> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User seller;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<Payment> payments = new ArrayList<>();

    public void addItem(SaleItem item) {
        this.items.add(item);
        item.setSale(this);
    }

    public void addPayment(Payment payment) {
        this.payments.add(payment);
        payment.setSale(this);
    }

    public BigDecimal getTotal() {
        BigDecimal total =  BigDecimal.ZERO;
        for (SaleItem item : items) {
            BigDecimal subtotal = item.getQuantity().multiply(item.getPrice());
            total = total.add(subtotal);
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getBalance() {
        BigDecimal paid = BigDecimal.ZERO;
        for (Payment payment : payments) {
            paid = paid.add(payment.getAmount());
        }
        var balance = getTotal().subtract(paid);
        return balance.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal updateStatus() {
        if (status == SaleStatus.CANCELLED) {
            return;
        }

    }
}
