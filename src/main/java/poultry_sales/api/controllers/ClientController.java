package poultry_sales.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poultry_sales.api.dto.ClientRequestDTO;
import poultry_sales.api.dto.ClientResponseDTO;
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
