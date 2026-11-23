package poultry_sales.api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poultry_sales.api.dto.UserRequestDTO;
import poultry_sales.api.dto.UserResponseDTO;
import poultry_sales.api.model.User;
import poultry_sales.api.services.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@Valid @RequestBody UserRequestDTO request) {
        User user = userService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponseDTO.from(user));
    }

    @GetMapping
    public List<UserResponseDTO> findAll() {
        return userService.findAll().stream()
                .map(UserResponseDTO::from)
                .toList();
    }

    @GetMapping("/{id}")
    public UserResponseDTO findById(@PathVariable Long id) {
        return UserResponseDTO.from(userService.findById(id));
    }
}
