package poultry_sales.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name is required")
    private String name;

    @Pattern(regexp = "[0-9]+", message = "phone must contain only digits")
    private String phone;

    private String address;

    // o que o cliente pegou e o que ele já pagou ficam presos ao cliente
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Charge> charges = new ArrayList<>();

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Payment> payments = new ArrayList<>();

    public void addCharge(Charge charge) {
        charges.add(charge);
        charge.setClient(this);
    }

    public void addPayment(Payment payment) {
        payments.add(payment);
        payment.setClient(this);
    }

    // saldo devedor: tudo que ele pegou menos tudo que ele já pagou
    public BigDecimal getBalance() {
        BigDecimal totalCharges = BigDecimal.ZERO;
        for (Charge charge : charges) {
            totalCharges = totalCharges.add(charge.getAmount());
        }

        BigDecimal totalPayments = BigDecimal.ZERO;
        for (Payment payment : payments) {
            totalPayments = totalPayments.add(payment.getAmount());
        }

        return totalCharges.subtract(totalPayments).setScale(2, RoundingMode.HALF_UP);
    }
}