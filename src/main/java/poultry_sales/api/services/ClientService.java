package poultry_sales.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poultry_sales.api.dto.ClientRequestDTO;
import poultry_sales.api.model.Client;
import poultry_sales.api.repositories.ClientRepository;
import poultry_sales.api.services.exceptions.ResourceNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    @Transactional
    public Client create(ClientRequestDTO request) {
        Client client = new Client();
        client.setName(request.name());
        client.setPhone(request.phone());
        client.setAddress(request.address());
        return clientRepository.save(client);
    }

    @Transactional(readOnly = true)
    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                "Client não encontrado. ID: " + id
        ));
    }

    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return clientRepository.findAll();
    }
}
