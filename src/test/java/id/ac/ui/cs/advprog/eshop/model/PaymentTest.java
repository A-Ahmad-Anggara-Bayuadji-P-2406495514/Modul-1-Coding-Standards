package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private Map<String, String> paymentData;
    private Order order;

    @BeforeEach
    void setUp() {
        this.paymentData = new HashMap<>();

        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        this.order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");
    }

    @Test
    void testCreatePaymentDefaultStatus() {
        Payment payment = new Payment("a2c62328-4a37-4664-83c7-f32db8620155",
                "VOUCHER_CODE", this.paymentData, this.order);

        assertEquals("a2c62328-4a37-4664-83c7-f32db8620155", payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertSame(this.paymentData, payment.getPaymentData());
        assertSame(this.order, payment.getOrder());
        assertEquals("WAITING_PAYMENT", payment.getStatus());
    }

    @Test
    void testCreatePaymentSuccessStatus() {
        Payment payment = new Payment("a2c62328-4a37-4664-83c7-f32db8620155",
                "BANK_TRANSFER", this.paymentData, this.order, "SUCCESS");
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreatePaymentInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("a2c62328-4a37-4664-83c7-f32db8620155",
                    "VOUCHER_CODE", this.paymentData, this.order, "MEOW");
        });
    }

    @Test
    void testSetStatusToRejected() {
        Payment payment = new Payment("a2c62328-4a37-4664-83c7-f32db8620155",
                "VOUCHER_CODE", this.paymentData, this.order);
        payment.setStatus("REJECTED");
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testSetStatusToInvalid() {
        Payment payment = new Payment("a2c62328-4a37-4664-83c7-f32db8620155",
                "VOUCHER_CODE", this.paymentData, this.order);
        assertThrows(IllegalArgumentException.class, () -> payment.setStatus("MEOW"));
    }
}