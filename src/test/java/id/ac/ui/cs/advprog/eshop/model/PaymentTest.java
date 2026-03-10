package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private Map<String, String> paymentData;
    private Order order;

    @BeforeEach
    void setUp() {
        this.paymentData = new HashMap<>();
        this.order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                new ArrayList<>(), 1708560000L, "Safira Sudrajat");
    }

    @Test
    void testCreatePaymentDefaultStatus() {
        Payment payment = new Payment("a2c62328-4a37-4664-83c7-f32db8620155",
                "VOUCHER_CODE", this.paymentData, this.order);
        assertEquals("a2c62328-4a37-4664-83c7-f32db8620155", payment.getId());
        assertEquals("VOUCHER_CODE", payment.getMethod());
        assertEquals(this.paymentData, payment.getPaymentData());
        assertEquals(this.order, payment.getOrder());
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