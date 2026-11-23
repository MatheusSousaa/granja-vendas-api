package poultry_sales.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PaymentRequestDTO(@NotNull @Positive BigDecimal amount) {
}
