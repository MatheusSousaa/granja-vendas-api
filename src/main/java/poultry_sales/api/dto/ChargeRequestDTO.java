package poultry_sales.api.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ChargeRequestDTO(@NotBlank String description,
                               @NotNull @Positive @Digits(integer = 8, fraction = 2) BigDecimal amount) {
}
