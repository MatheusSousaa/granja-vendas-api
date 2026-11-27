package poultry_sales.api.dto;

import poultry_sales.api.model.Client;

import java.math.BigDecimal;

public record ClientResponseDTO(Long id, String name, String phone, String address, BigDecimal balance) {

    public static ClientResponseDTO from(Client client) {
        return new ClientResponseDTO(client.getId(), client.getName(), client.getPhone(),
                client.getAddress(), client.getBalance());
    }
}
