package poultry_sales.api.dto;

import poultry_sales.api.model.Sale;
import poultry_sales.api.model.enums.SaleStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record SaleResponseDTO(
        Long saleId,
        LocalDate saleDate,
        SaleStatus status,
        String clientName,
        List<SaleItemResponseDTO> items,
        BigDecimal total,
        BigDecimal paid,
        BigDecimal balance
) {

    public static SaleResponseDTO from(Sale sale) {
        return new SaleResponseDTO(
                sale.getId(),
                sale.getSaleDate(),
                sale.getStatus(),
                sale.getClient() != null ? sale.getClient().getName() : null,
                sale.getItems().stream().map(SaleItemResponseDTO::from).toList(),
                sale.getTotal(),
                sale.getPaid(),
                sale.getBalance()
        );
    }
}