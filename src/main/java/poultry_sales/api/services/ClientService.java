package poultry_sales.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poultry_sales.api.dto.ChargeRequestDTO;
import poultry_sales.api.dto.ClientRequestDTO;
import poultry_sales.api.dto.PaymentRequestDTO;
import poultry_sales.api.model.Charge;
import poultry_sales.api.model.Client;
import poultry_sales.api.model.Payment;
import poultry_sales.api.repositories.ClientRepository;
import poultry_sales.api.services.exceptions.BusinessRuleException;
import poultry_sales.api.services.exceptions.ResourceNotFoundException;

import java.math.BigDecimal;
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

    @Transactional
    public Client addPayment(Long clientId, PaymentRequestDTO request) {
        Client client = findById(clientId);

        if (request.amount().compareTo(client.getBalance()) > 0) {
            throw new BusinessRuleException(
                    "Pagamento maior que o saldo devedor: " + client.getBalance());
        }

        Payment payment = new Payment();
        payment.setAmount(request.amount());
        client.addPayment(payment);

        return clientRepository.save(client);
    }

    @Transactional
    public Client addCharge(Long clientId, ChargeRequestDTO request) {
        Client client = findById(clientId);

        Charge charge = new Charge();
        charge.setAmount(request.amount());
        charge.setDescription(request.description());

        client.addCharge(charge);

        return clientRepository.save(client);
    }

    @Transactional(readOnly = true)
    public List<Client> findOwing() {
        return clientRepository.findAll()
                .stream()
                .filter(client -> client.getBalance().compareTo(BigDecimal.ZERO) > 0)
                .toList();
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
