package customer.rewards.interview.controllers;

import customer.rewards.interview.models.Order;
import customer.rewards.interview.models.Orders;
import customer.rewards.interview.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rewards")
public class RewardsController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RewardsController.class);

    @Autowired
    OrderService orderService;

    @GetMapping
    public ResponseEntity<Map> getRewards() {
        LOGGER.debug("Getting rewards for the orders loaded from orders.txt");

        List<Order> orders = Orders.getAllOrders();
        return new ResponseEntity<>(orderService.getTotalRewardPoints(orders), HttpStatus.OK);
    }

}
