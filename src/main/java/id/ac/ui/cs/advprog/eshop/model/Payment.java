package id.ac.ui.cs.advprog.eshop.model;
import java.util.Map;

public class Payment {
    public Payment(String id, String method, Map<String, String> paymentData, Order order) {}
    public Payment(String id, String method, Map<String, String> paymentData, Order order, String status) {}
    public String getStatus() { return null; }
    public String getId() { return null; }
    public String getMethod() { return null; }
    public Map<String, String> getPaymentData() { return null; }
    public Order getOrder() { return null; }
    public void setStatus(String status) {}
}