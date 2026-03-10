package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    PaymentRepository paymentRepository;
    Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        paymentData = new HashMap<>();
    }

    @Test
    void testSaveCreate() {
        Payment payment = new Payment("p1", "VOUCHER_CODE", paymentData, null);
        Payment result = paymentRepository.save(payment);

        assertEquals(payment, result);
        Payment found = paymentRepository.findById("p1");
        assertNotNull(found);
        assertEquals(payment.getId(), found.getId());
        assertEquals(payment.getMethod(), found.getMethod());
    }

    @Test
    void testSaveUpdate() {
        // First save
        Payment payment1 = new Payment("p1", "VOUCHER_CODE", paymentData, null);
        paymentRepository.save(payment1);

        // Update with same ID but different method
        Payment payment2 = new Payment("p1", "BANK_TRANSFER", paymentData, null);
        Payment result = paymentRepository.save(payment2);

        assertEquals(payment2, result);
        Payment found = paymentRepository.findById("p1");
        assertEquals("BANK_TRANSFER", found.getMethod());

        // Ensure the list size is still 1 (it updated, didn't just add)
        assertEquals(1, paymentRepository.findAll().size());
    }

    @Test
    void testFindByIdIfIdFound() {
        Payment payment1 = new Payment("p1", "VOUCHER_CODE", paymentData, null);
        Payment payment2 = new Payment("p2", "BANK_TRANSFER", paymentData, null);
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);

        Payment found = paymentRepository.findById("p2");
        assertEquals(payment2, found);
    }

    @Test
    void testFindByIdIfIdNotFound() {
        paymentRepository.save(new Payment("p1", "VOUCHER_CODE", paymentData, null));
        assertNull(paymentRepository.findById("non-existent-id"));
    }

    @Test
    void testFindAllIfEmpty() {
        List<Payment> all = paymentRepository.findAll();
        assertTrue(all.isEmpty());
    }

    @Test
    void testFindAllIfNotEmpty() {
        paymentRepository.save(new Payment("p1", "VOUCHER_CODE", paymentData, null));
        paymentRepository.save(new Payment("p2", "BANK_TRANSFER", paymentData, null));

        List<Payment> all = paymentRepository.findAll();
        assertEquals(2, all.size());
        assertEquals("p1", all.get(0).getId());
        assertEquals("p2", all.get(1).getId());
    }
}