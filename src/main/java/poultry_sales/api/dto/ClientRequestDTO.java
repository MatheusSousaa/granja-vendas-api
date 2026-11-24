package poultry_sales.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ClientRequestDTO(@NotBlank String name,
                               @Pattern(regexp = "[0-9]+") String phone,
                               String address) {
}
