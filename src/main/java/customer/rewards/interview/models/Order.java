package customer.rewards.interview.models;

import java.time.LocalDate;

public class Order {

    private int orderId;
    private String customerName;
    private double totalAmount;
    private LocalDate orderedAt;

    public Order(int id, String customerName, double totalAmount, LocalDate orderDate) {
        this.orderId = id;
        this.customerName = customerName;
        this.totalAmount = totalAmount;
        this.orderedAt = orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String name) {
        this.customerName = name;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(LocalDate orderedAt) {
        this.orderedAt = orderedAt;
    }


}
