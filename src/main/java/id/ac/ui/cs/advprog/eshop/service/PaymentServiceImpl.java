package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.*;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired private PaymentRepository paymentRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> data) {
        String status = "SUCCESS";

        if ("VOUCHER_CODE".equals(method)) {
            String code = data.get("voucherCode");
            if (code == null || code.length() != 16 || !code.startsWith("ESHOP") ||
                    code.chars().filter(Character::isDigit).count() != 8) {
                status = "REJECTED";
            }
        } else if ("BANK_TRANSFER".equals(method)) {
            if (isInvalid(data, "bankName") || isInvalid(data, "referenceCode")) {
                status = "REJECTED";
            }
        }

        Payment payment = new Payment(UUID.randomUUID().toString(), method, data, order, status);

        // Relationship Sync
        if ("SUCCESS".equals(status)) order.setStatus("SUCCESS");
        else if ("REJECTED".equals(status)) order.setStatus("FAILED");

        return paymentRepository.save(payment);
    }

    private boolean isInvalid(Map<String, String> data, String key) {
        String val = data.get(key);
        return val == null || val.isBlank();
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        payment.setStatus(status);
        if ("SUCCESS".equals(status)) payment.getOrder().setStatus("SUCCESS");
        else if ("REJECTED".equals(status)) payment.getOrder().setStatus("FAILED");
        return payment;
    }

    @Override public Payment getPayment(String id) { return paymentRepository.findById(id); }
    @Override public List<Payment> getAllPayments() { return paymentRepository.findAll(); }
}