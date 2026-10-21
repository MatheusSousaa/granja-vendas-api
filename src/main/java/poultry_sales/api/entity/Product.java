package poultry_sales.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name is required")
    private String name;

    @Column(precision = 10, scale = 3)
    @NotNull
    @PositiveOrZero
    private BigDecimal quantity;

    @Column(precision = 10, scale = 2)
    @NotNull
    @Positive
    private BigDecimal price;

    private LocalDate expirationDate;
    private String type;
}
