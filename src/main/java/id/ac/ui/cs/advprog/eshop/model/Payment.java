package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import java.util.Map;
import java.util.Arrays;

@Getter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;
    private Order order;

    public Payment(String id, String method, Map<String, String> paymentData, Order order) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.order = order;
        this.setStatus("WAITING_PAYMENT");
    }

    public Payment(String id, String method, Map<String, String> paymentData, Order order, String status) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.order = order;
        this.setStatus(status);
    }

    public void setStatus(String status) {
        String[] validStatuses = {"WAITING_PAYMENT", "SUCCESS", "REJECTED"};
        if (Arrays.stream(validStatuses).noneMatch(s -> s.equals(status))) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
        this.status = status;
    }
}