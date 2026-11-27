package poultry_sales.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poultry_sales.api.dto.ChargeRequestDTO;
import poultry_sales.api.dto.ClientRequestDTO;
import poultry_sales.api.dto.ClientResponseDTO;
import poultry_sales.api.dto.PaymentRequestDTO;
import poultry_sales.api.model.Client;
import poultry_sales.api.services.ClientService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientResponseDTO> create(@Valid @RequestBody ClientRequestDTO request) {
        Client client = clientService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ClientResponseDTO.from(client));
    }

    @PostMapping("/{id}/charges")
    public ResponseEntity<ClientResponseDTO> addCharge(
            @PathVariable Long id,
            @Valid @RequestBody ChargeRequestDTO request) {

        Client client = clientService.addCharge(id, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ClientResponseDTO.from(client));
    }

    @PostMapping("/{id}/payments")
    public ResponseEntity<ClientResponseDTO> addPayment(
            @PathVariable Long id,
            @Valid @RequestBody PaymentRequestDTO request) {

        Client client = clientService.addPayment(id, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ClientResponseDTO.from(client));
    }

    @GetMapping("/owing")
    public List<ClientResponseDTO> findOwing() {
        return clientService.findOwing()
                .stream()
                .map(ClientResponseDTO::from)
                .toList();
    }

    @GetMapping("/{id}")
    public ClientResponseDTO findById(@PathVariable Long id) {
        return ClientResponseDTO.from(clientService.findById(id));
    }

    @GetMapping
    public List<ClientResponseDTO> findAll() {
        return clientService.findAll().stream().
                map(ClientResponseDTO::from).
                toList();
        
    }
}
