package poultry_sales.api.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poultry_sales.api.dto.UserRequestDTO;
import poultry_sales.api.model.User;
import poultry_sales.api.repositories.UserRepository;
import poultry_sales.api.services.exceptions.BusinessRuleException;
import poultry_sales.api.services.exceptions.ResourceNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User create(UserRequestDTO request) {
        // o e-mail é único: sem esta checagem, o erro viria do banco como um 500 confuso
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new BusinessRuleException("Já existe um usuário com esse e-mail");
        }

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(request.password()); // texto puro por enquanto; vira hash com o Spring Security
        user.setRole(request.role());

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuário não encontrado. ID: " + id
                ));
    }
}