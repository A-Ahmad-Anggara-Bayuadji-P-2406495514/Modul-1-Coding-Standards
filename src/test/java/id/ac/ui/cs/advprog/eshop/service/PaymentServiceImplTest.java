package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.*;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {
    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    Order order;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("p1");
        product.setProductQuantity(1);
        products.add(product);

        this.order = new Order("o1", products, 1708560000L, "Safira Sudrajat");
    }

    @Test
    void testAddPaymentVoucherHappyPath() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABC5678");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArguments()[0]);

        Payment result = paymentService.addPayment(order, "VOUCHER_CODE", data);
        assertEquals("SUCCESS", result.getStatus());
        assertEquals("SUCCESS", order.getStatus());
    }

    @Test
    void testAddPaymentVoucherInvalidLength() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP123");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArguments()[0]);

        Payment result = paymentService.addPayment(order, "VOUCHER_CODE", data);
        assertEquals("REJECTED", result.getStatus());
        assertEquals("FAILED", order.getStatus());
    }

    @Test
    void testAddPaymentVoucherInvalidPrefix() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "BANAN1234ABC5678");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArguments()[0]);

        Payment result = paymentService.addPayment(order, "VOUCHER_CODE", data);
        assertEquals("REJECTED", result.getStatus());
    }

    @Test
    void testAddPaymentVoucherInsufficientDigits() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABC567A"); // Only 7 digits

        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArguments()[0]);

        Payment result = paymentService.addPayment(order, "VOUCHER_CODE", data);
        assertEquals("REJECTED", result.getStatus());
    }

    @Test
    void testAddPaymentBankHappyPath() {
        Map<String, String> data = new HashMap<>();
        data.put("bankName", "BCA");
        data.put("referenceCode", "REF12345");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArguments()[0]);

        Payment result = paymentService.addPayment(order, "BANK_TRANSFER", data);
        assertEquals("SUCCESS", result.getStatus());
        assertEquals("SUCCESS", order.getStatus());
    }

    @Test
    void testAddPaymentBankMissingBankName() {
        Map<String, String> data = new HashMap<>();
        data.put("referenceCode", "REF12345");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArguments()[0]);

        Payment result = paymentService.addPayment(order, "BANK_TRANSFER", data);
        assertEquals("REJECTED", result.getStatus());
        assertEquals("FAILED", order.getStatus());
    }

    @Test
    void testAddPaymentBankEmptyReference() {
        Map<String, String> data = new HashMap<>();
        data.put("bankName", "BCA");
        data.put("referenceCode", "");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArguments()[0]);

        Payment result = paymentService.addPayment(order, "BANK_TRANSFER", data);
        assertEquals("REJECTED", result.getStatus());
    }

    @Test
    void testSetStatusToSuccessUpdatesOrder() {
        Payment payment = new Payment("p1", "BANK_TRANSFER", new HashMap<>(), order);
        paymentService.setStatus(payment, "SUCCESS");
        assertEquals("SUCCESS", order.getStatus());
    }

    @Test
    void testSetStatusToRejectedUpdatesOrder() {
        Payment payment = new Payment("p1", "BANK_TRANSFER", new HashMap<>(), order);
        paymentService.setStatus(payment, "REJECTED");
        assertEquals("FAILED", order.getStatus());
    }

    @Test
    void testGetPaymentById() {
        Payment payment = new Payment("p1", "VOUCHER_CODE", new HashMap<>(), order);
        when(paymentRepository.findById("p1")).thenReturn(payment);

        Payment result = paymentService.getPayment("p1");
        assertEquals(payment, result);
    }
}