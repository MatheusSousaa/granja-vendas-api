package poultry_sales.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import poultry_sales.api.model.enums.Role;

public record UserRequestDTO(@NotBlank String name,
                             @NotBlank @Email String email,
                             @NotBlank String password,
                             @NotNull Role role) {
}
