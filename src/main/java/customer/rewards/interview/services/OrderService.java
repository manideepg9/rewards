package customer.rewards.interview.services;

import customer.rewards.interview.models.Order;
import customer.rewards.interview.models.Orders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    @Value("${orders.source}")
    private String ordersFilePath;

    private Map<Integer, Integer> rewardsDefinitionMap;
    private int[] rewardThresholdAmountSorted;

    @PostConstruct
    public void init() throws Exception {

        LOGGER.info("Importing orders from: "+ ordersFilePath);
        List<String> ordersInput;

        rewardsDefinitionMap = new HashMap<>();
        rewardsDefinitionMap.put(100, 2);
        rewardsDefinitionMap.put(50, 1);

        rewardThresholdAmountSorted = new int[] {100, 50};

        Orders orders = new Orders();
        try {
            ordersInput = Files.readAllLines(ResourceUtils.getFile("classpath:" + ordersFilePath).toPath());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M-d-yyyy");
            for (String orderFromInput : ordersInput) {
                String parts[] = orderFromInput.split(",");
                int id = Integer.parseInt(parts[0].trim());
                String customerName = parts[1].trim();
                double amount = Double.parseDouble(parts[2].trim());
                LocalDate date = LocalDate.parse(parts[3].trim(), formatter);
                orders.addOrder(new Order(id, customerName, amount, date));
            }
        } catch (IOException ex) {
            LOGGER.error("File not found: ",
                    this.ordersFilePath);
            LOGGER.error(ex.toString());
            throw ex;
        } catch (Exception ex) {
            LOGGER.error("Error while loading order transaction data from: ",
                    this.ordersFilePath);
            LOGGER.error(ex.toString());
            throw ex;
        }

        LOGGER.info("Order Service init completed");

        //LOGGER.info("getTotalRewardPoints: " + this.getTotalRewardPoints(orders.getAllOrders()).toString());
    }

    public Map<String, Map<String, Integer>> getTotalRewardPoints(List<Order> orders) {
        Map<String, Map<String, Integer>> result = new HashMap<>();

        Map<String, Integer> customerTotalRewards = new HashMap<>();

        for (Order o: orders) {
            int currentOrderRewardPoints = getRewardPointsForOrder(o);
            String month = o.getOrderedAt().getMonth().toString();
            int year = o.getOrderedAt().getYear();
            String customerName = o.getCustomerName();
            String monthKey = month + "-" +year;

            if (result.containsKey(customerName)) {
                Map<String, Integer> customerMonthlyRewards = result.get(customerName);
                int currentMonthRewardPointsSoFar = customerMonthlyRewards.getOrDefault(monthKey, 0);
                int totalRewardPointsSoFar = customerMonthlyRewards.getOrDefault("Total", 0);
                customerMonthlyRewards.put(monthKey, currentMonthRewardPointsSoFar + currentOrderRewardPoints);
                customerMonthlyRewards.put("Total", totalRewardPointsSoFar + currentOrderRewardPoints);

            } else {
                Map<String, Integer> customerMonthlyRewards = new HashMap<>();
                customerMonthlyRewards.put(monthKey, currentOrderRewardPoints);
                customerMonthlyRewards.put("Total", currentOrderRewardPoints);
                result.put(customerName, customerMonthlyRewards);
            }
        }

        /* Result structure will be like:

            {
                Customer1:
                    {
                        MARCH2020: 15,
                        Total: 120,
                        APRIL2020:105
                    },
                Customer2:
                    {
                        MARCH2021: 150,
                        Total: 180,
                        APRIL2020: 30
                    },
            }
         */
        return result;
    }

    private int getRewardPointsForOrder(Order order) {
        int amount = (int) order.getTotalAmount();
        int rewards = 0;

        if (amount > 100) {
            int amountOver100 = amount - 100;
            rewards += amountOver100 * 2; // rewards for amount > 100;
            rewards += 50; // rewards for $51-100; 50 * 1
        } else if (amount > 50) {
            int amountOver50 = amount - 50;
            rewards += amountOver50;
        }

        return rewards;
    }

}
