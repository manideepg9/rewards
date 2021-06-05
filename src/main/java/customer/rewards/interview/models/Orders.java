package customer.rewards.interview.models;

import java.util.ArrayList;
import java.util.List;

public class Orders {
    private static List<Order> allOrders;

    public Orders() {
        allOrders = new ArrayList<>();
    }

    public static List<Order> getAllOrders() {
        return allOrders;
    }

    public void addOrder(Order o) {
        this.allOrders.add(o);
    }

}
