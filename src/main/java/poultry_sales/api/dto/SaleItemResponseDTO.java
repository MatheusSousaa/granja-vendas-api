package poultry_sales.api.dto;

import poultry_sales.api.model.SaleItem;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record SaleItemResponseDTO(
        String productName,
        BigDecimal quantity,
        BigDecimal price,
        BigDecimal subtotal
) {

    public static SaleItemResponseDTO from(SaleItem item) {
        return new SaleItemResponseDTO(
                item.getProduct().getName(),
                item.getQuantity(),
                item.getPrice(),
                item.getQuantity().multiply(item.getPrice())
                        .setScale(2, RoundingMode.HALF_UP)
        );
    }
}