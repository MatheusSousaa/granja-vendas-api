package poultry_sales.api.dto;

import poultry_sales.api.model.Charge;
import poultry_sales.api.model.Payment;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ChargeResponseDTO(Long id, String description, BigDecimal amount, LocalDate date) {

    public static ChargeResponseDTO from(Charge charge) {
        return new ChargeResponseDTO(charge.getId(), charge.getDescription(), charge.getAmount(), charge.getDate());
    }
}