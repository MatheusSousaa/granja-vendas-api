package poultry_sales.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poultry_sales.api.dto.PaymentRequestDTO;
import poultry_sales.api.dto.SaleRequestDTO;
import poultry_sales.api.dto.SaleResponseDTO;
import poultry_sales.api.model.Sale;
import poultry_sales.api.services.SaleService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sales")
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    public ResponseEntity<SaleResponseDTO> create(@Valid @RequestBody SaleRequestDTO request) {
        Sale sale = saleService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(SaleResponseDTO.from(sale));
    }

    @GetMapping
    public List<SaleResponseDTO> findAll() {
        return saleService.findAll().stream()
                .map(SaleResponseDTO::from)
                .toList();
    }

    @GetMapping("/{id}")
    public SaleResponseDTO findById(@PathVariable Long id) {
        return SaleResponseDTO.from(saleService.findById(id));
    }

    @PostMapping("/{id}/payments")
    public ResponseEntity<SaleResponseDTO> addPayment(
            @PathVariable Long id,
            @Valid @RequestBody PaymentRequestDTO request) {

        Sale sale = saleService.addPayment(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(SaleResponseDTO.from(sale));
    }

}
