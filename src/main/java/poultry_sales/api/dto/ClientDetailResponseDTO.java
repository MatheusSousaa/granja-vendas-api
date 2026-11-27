package poultry_sales.api.dto;

import poultry_sales.api.model.Client;

import java.math.BigDecimal;
import java.util.List;

public record ClientDetailResponseDTO(Long id, String name, String phone, String address,
                                      BigDecimal balance,
                                      List<ChargeResponseDTO> charges,
                                      List<PaymentResponseDTO> payments) {

    public static ClientDetailResponseDTO from(Client client) {
        return new ClientDetailResponseDTO(
                client.getId(), client.getName(), client.getPhone(), client.getAddress(),
                client.getBalance(),
                client.getCharges().stream().map(ChargeResponseDTO::from).toList(),
                client.getPayments().stream().map(PaymentResponseDTO::from).toList());
    }
}
