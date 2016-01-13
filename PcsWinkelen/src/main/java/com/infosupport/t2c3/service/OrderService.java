package com.infosupport.t2c3.service;

import com.infosupport.t2c3.domain.customers.Address;
import com.infosupport.t2c3.domain.customers.CustomerData;
import com.infosupport.t2c3.domain.orders.Order;
import com.infosupport.t2c3.domain.orders.OrderItem;
import com.infosupport.t2c3.domain.orders.OrderStatus;
import com.infosupport.t2c3.domain.products.Product;
import com.infosupport.t2c3.repositories.OrderRepository;
import com.infosupport.t2c3.repositories.ProductRepository;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Windows 7 on 6-1-2016.
 */

@RestController
@RequestMapping(value = "/order", produces = "application/json")
@CrossOrigin
@Setter
public class OrderService {

    //TODO remove with init function
    private static final int ADD_ORDERS = 10;

    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private ProductRepository productRepo;


    /**
     * Get all the orders from the repo.
     *
     * @return All the orders
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    /**
     * Send order to the backend. Repo passes it to the dababase.
     *
     * @param order The order to be persisted.
     * @return the order
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public Order placeOrder(@RequestBody Order order) {

        Order newOrder = calculatePrices(order);
        newOrder.setStatus(OrderStatus.PLACED);
        orderRepo.save(newOrder);

        //TODO send succes message
        return newOrder;
    }

    /**
     * Calculates and sets all prices in the order instance.
     *
     * @param order the order
     * @return the order with prices set
     */
    private Order calculatePrices(Order order) {
        order.setTotalPrice(new BigDecimal(0.0));

        for (OrderItem item : order.getItems()) {
            Product product = productRepo.findOne(item.getProduct().getId());
            item.setPrice(product.getPrice());
            BigDecimal pricePerItem = item.getPrice().multiply(new BigDecimal(item.getAmount()));
            BigDecimal totalPrice = order.getTotalPrice().add(pricePerItem);
            order.setTotalPrice(totalPrice);
        }
        return order;
    }

    /**
     * Initialize this product service.
     */
    public void init() {
        //TODO: Remove this, is just adding random data
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < 2; i++) {
            List<OrderItem> items = new ArrayList<>();
            for (int a = 0; a < 3; a++) {
                items.add(new OrderItem(
                        null,
                        random.nextInt(4) + 1,
                        productRepo.findOne(Long.valueOf(random.nextInt(15) + 1))
                ));
            }

            Order order = new Order(
                    null,
                    OrderStatus.PLACED,
                    items,
                    new CustomerData(
                            "Remco",
                            "Groenenboom",
                            "remco@email.com",
                            new Address(
                                  "city",
                                    "street",
                                    "6",
                                    "zipcode")
                            )

                    );
            calculatePrices(order);
            orderRepo.save(order);
        }
    }

}
