package poultry_sales.api.dto;

import poultry_sales.api.model.Payment;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentResponseDTO(Long id, BigDecimal amount, LocalDate paymentDate) {

    public static PaymentResponseDTO from(Payment payment) {
        return new PaymentResponseDTO(payment.getId(), payment.getAmount(), payment.getPaymentDate());
    }
}
