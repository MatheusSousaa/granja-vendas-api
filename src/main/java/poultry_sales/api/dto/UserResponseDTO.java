package poultry_sales.api.dto;

import poultry_sales.api.model.User;
import poultry_sales.api.model.enums.Role;

public record UserResponseDTO(Long id, String email, Role role) {

    public static UserResponseDTO from(User user) {
        return new UserResponseDTO(user.getId(), user.getEmail(), user.getRole());
    }
}
