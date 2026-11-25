import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import poultry_sales.api.model.Payment;
import poultry_sales.api.model.Product;
import poultry_sales.api.model.Sale;
import poultry_sales.api.model.SaleItem;
import poultry_sales.api.model.enums.SaleStatus;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SaleTest {

    private Sale sale;

    @BeforeEach
    void setUp() {
        Product product = new Product();
        product.setName("Frango");

        SaleItem item = new SaleItem();
        item.setProduct(product);
        item.setQuantity(new BigDecimal("1.350"));
        item.setPrice(new BigDecimal("15.00"));

        sale = new Sale();
        sale.addItem(item);
    }

    @Test
    void shouldCalculateTotal() {

        assertEquals(new BigDecimal("20.25"), sale.getTotal());
    }

    @Test
    void shouldBePaidWhenPaymentCoversTotal() {
        Payment payment = new Payment();
        payment.setAmount(new BigDecimal("20.25"));

        sale.addPayment(payment);

        assertEquals(SaleStatus.PAGO, sale.getStatus());
        assertEquals(new BigDecimal("0.00"), sale.getBalance());
    }

    @Test
    void shouldKeepCancelledStatusAfterPayment() {
        sale.setStatus(SaleStatus.CANCELADO);

        Payment payment = new Payment();
        payment.setAmount(new BigDecimal("10.00"));
        sale.addPayment(payment);

        assertEquals(SaleStatus.CANCELADO, sale.getStatus());
    }
}