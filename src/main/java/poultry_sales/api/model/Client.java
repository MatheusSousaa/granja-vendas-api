package poultry_sales.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name = "clients")
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name is required")
    private String name;

    @Email
    @NotBlank
    private String email;

    @Pattern(regexp = "[0-9]+")
    private String phone;

    @CPF
    @NotBlank
    private String cpf;

}
