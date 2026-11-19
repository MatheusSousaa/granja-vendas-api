package poultry_sales.api.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record SaleItemRequestDTO(@NotNull Long productId,
                                 @NotNull @Positive @Digits(integer = 3, fraction = 2) BigDecimal quantity) {}
