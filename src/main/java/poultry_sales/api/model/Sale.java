package poultry_sales.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import poultry_sales.api.model.enums.SaleStatus;

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
    private SaleStatus status = SaleStatus.PENDENTE;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<SaleItem> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User seller;

    @ManyToOne(fetch = FetchType.LAZY)
    private Client client;

    @OneToMany(mappedBy = "sale", cascade = CascadeType.ALL)
    private List<Payment> payments = new ArrayList<>();

    // Mantém o relacionamento entre a venda e o item sincronizado
    // para que ambos reconheçam a qual venda o item pertence.

    public void addItem(SaleItem item) {
        this.items.add(item);
        item.setSale(this);
        updateStatus();
    }

    // Atualiza o status após cada pagamento para manter a situação
    // da venda consistente com os valores pagos e o saldo restante.

    public void addPayment(Payment payment) {
        this.payments.add(payment);
        payment.setSale(this);
        updateStatus();
    }

    // Utiliza BigDecimal para evitar problemas de precisão
    // comuns em cálculos monetários com números de ponto flutuante.

    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (SaleItem item : items) {
            BigDecimal subtotal = item.getQuantity().multiply(item.getPrice());
            total = total.add(subtotal);
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    // Permite acompanhar o valor efetivamente recebido,
    // inclusive quando a venda é paga em várias parcelas.

    public BigDecimal getPaid() {
        var paid = BigDecimal.ZERO;
        for (Payment payment : payments) {
            paid = paid.add(payment.getAmount());
        }
        return paid;
    }

    // Calcula o saldo restante para distinguir vendas pendentes,
    // parcialmente pagas e totalmente quitadas.

    public BigDecimal getBalance() {
        var balance = getTotal().subtract(getPaid());
        return balance.setScale(2, RoundingMode.HALF_UP);
    }

    // Impede que pagamentos alterem o status de uma venda cancelada,
    // preservando a regra de negócio de que o cancelamento tem prioridade.

    public void updateStatus() {
        if (status == SaleStatus.CANCELADO) {
            return;
        }
        if (getBalance().compareTo(BigDecimal.ZERO) <= 0) {
            status = SaleStatus.PAGO;
        } else if (getPaid().compareTo(BigDecimal.ZERO) > 0) {
            status = SaleStatus.PARCIALMENTE_PAGO;
        } else {
            status = SaleStatus.PENDENTE;
        }
    }
}
