package poultry_sales.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poultry_sales.api.dto.SaleItemRequestDTO;
import poultry_sales.api.dto.SaleRequestDTO;
import poultry_sales.api.model.*;
import poultry_sales.api.repositories.ClientRepository;
import poultry_sales.api.repositories.ProductRepository;
import poultry_sales.api.repositories.SaleRepository;
import poultry_sales.api.repositories.UserRepository;
import poultry_sales.api.services.exceptions.BusinessRuleException;
import poultry_sales.api.services.exceptions.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private  final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;


    @Transactional
    public Sale create(SaleRequestDTO request) {
        User seller = userRepository.findById(request.sellerId())
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor não encontrado. ID: " + request.sellerId()));

        Client client = null;
        if (request.clientId() != null) {
            client = clientRepository.findById(request.clientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado. ID: " + request.clientId()));
        }

        Sale sale = new Sale();
        sale.setSeller(seller);
        sale.setClient(client);

        for (SaleItemRequestDTO itemRequest : request.items()) {
            Product product = productRepository.findById(itemRequest.productId())
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado. ID: " + itemRequest.productId()));

            // regra 1: produto vencido não pode ser vendido
            if (product.getExpirationDate() != null
                    && product.getExpirationDate().isBefore(LocalDate.now())) {
                throw new BusinessRuleException("O Produto está vencido, não pode ser vendido");
            }

            // regra 2: estoque insuficiente (a mensagem diz quanto tem)
            if (product.getQuantity().compareTo(itemRequest.quantity()) < 0) {
                throw new BusinessRuleException("Estoque insuficiente. Disponível: " + product.getQuantity());
            }

            SaleItem saleItem = new SaleItem();
            saleItem.setProduct(product);
            saleItem.setQuantity(itemRequest.quantity());
            saleItem.setPrice(product.getPrice());
            sale.addItem(saleItem);
            product.setQuantity(product.getQuantity().subtract(itemRequest.quantity()));

        }

        return saleRepository.save(sale);
    }

    @Transactional(readOnly = true)
    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Sale findById(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Venda não encontrada. ID: " + id
                ));
    }
}