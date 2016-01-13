package com.infosupport.t2c3.service;

import com.infosupport.t2c3.domain.orders.Order;
import com.infosupport.t2c3.domain.orders.OrderItem;
import com.infosupport.t2c3.domain.orders.OrderStatus;
import com.infosupport.t2c3.domain.products.Product;
import com.infosupport.t2c3.domain.products.Supply;
import com.infosupport.t2c3.exceptions.ItemNotFoundException;
import com.infosupport.t2c3.exceptions.NoSupplyException;
import com.infosupport.t2c3.repositories.OrderRepository;
import com.infosupport.t2c3.repositories.ProductRepository;
import com.infosupport.t2c3.repositories.SupplyHandler;
import java.math.BigDecimal;
import java.util.List;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Windows 7 on 6-1-2016.
 */

@RestController
@RequestMapping(value = "/order", produces = "application/json")
@CrossOrigin
@Setter
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private SupplyHandler supplyBean;


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
    @Transactional(rollbackFor = {ItemNotFoundException.class, NoSupplyException.class})
    public Order placeOrder(@RequestBody Order order) throws ItemNotFoundException, NoSupplyException {
        //Calculate the order
        Order newOrder = calculatePrices(order);

        //Decrease Supply
        for (OrderItem orderItem : newOrder.getItems()) {
            supplyBean.decreaseStock(orderItem.getProduct(), orderItem.getAmount());
        }

        //Save the order
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
    private Order calculatePrices(Order order) throws ItemNotFoundException {
        order.setTotalPrice(new BigDecimal(0.0));

        for (OrderItem item : order.getItems()) {
            Product product = productRepo.findOne(item.getProduct().getId());
            if (product == null) {
                throw new ItemNotFoundException("No Product with ID " + item.getProduct().getId());
            }

            //Update product & prices
            item.setProduct(product);
            item.setPrice(product.getPrice());
            BigDecimal pricePerItem = item.getPrice().multiply(new BigDecimal(item.getAmount()));
            BigDecimal totalPrice = order.getTotalPrice().add(pricePerItem);
            order.setTotalPrice(totalPrice);
        }
        return order;
    }
}
