package com.infosupport.t2c3.service;

import com.infosupport.t2c3.data.BasicRepository;
import com.infosupport.t2c3.domain.customers.Address;
import com.infosupport.t2c3.domain.customers.CustomerData;
import com.infosupport.t2c3.domain.orders.Order;
import com.infosupport.t2c3.domain.orders.OrderItem;
import com.infosupport.t2c3.domain.orders.OrderStatus;
import com.infosupport.t2c3.domain.products.Product;
import com.infosupport.t2c3.repositories.OrderRepository;
import com.infosupport.t2c3.repositories.ProductRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Windows 7 on 6-1-2016.
 */

@Setter
@RestController
@ExposesResourceFor(Order.class)
@RequestMapping(value = "/orders", produces = "application/json")
public class OrderService extends AbsRestService<Order> {

    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private ProductRepository productRepo;


    /**
     * Send order to the backend. Repo passes it to the dababase.
     *
     * @param order The order to be persisted.
     * @return the order
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public Order placeOrder(@RequestBody Order order) {
        Order newOrder = verifyOrder(order);
        orderRepo.save(newOrder);

        addSelfLink(newOrder);

        return newOrder;
    }

    @RequestMapping(path = "/fake")
    public Order fakeOrder() {
        List<Product> products = productRepo.findAll();
        Random random = new Random();
        List<OrderItem> items = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Product product = products.get(random.nextInt(products.size()));
            items.add(new OrderItem(product.getPrice(), random.nextInt(5) + 1, product));
        }

        Address address = new Address("A", "B", "1", "3333CA");
        CustomerData data = new CustomerData("First", "Last", "Email", address);

        Order order = new Order(new BigDecimal(10), OrderStatus.PLACED, items, data);
        return order;
    }

    /**
     * Calculates and sets all prices in the order instance.
     *
     * @param order the order
     * @return the order with prices set
     */
    private Order verifyOrder(Order order) {
        order.setTotalPrice(new BigDecimal(0.0));

        for (OrderItem item : order.getItems()) {
            Product product = productRepo.findOne(item.getProduct().getId());
            product = addSelfLink(product);
            item.setProduct(product);

            item.setPrice(product.getPrice());
            BigDecimal pricePerItem = item.getPrice().multiply(new BigDecimal(item.getAmount()));
            BigDecimal totalPrice = order.getTotalPrice().add(pricePerItem);
            order.setTotalPrice(totalPrice);
        }

        return order;
    }

    @Override
    public BasicRepository<Order> provideRepo() {
        return orderRepo;
    }
}
