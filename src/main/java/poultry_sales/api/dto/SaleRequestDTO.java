package poultry_sales.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record SaleRequestDTO(Long sellerId,
                             Long clientId,
                             @NotEmpty @Valid List<SaleItemRequestDTO> items) {}
