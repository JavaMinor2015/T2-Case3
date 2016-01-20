package com.infosupport.t2c3.init;

import com.infosupport.t2c3.domain.accounts.Customer;
import com.infosupport.t2c3.domain.orders.*;
import com.infosupport.t2c3.domain.products.Category;
import com.infosupport.t2c3.domain.products.Product;
import com.infosupport.t2c3.domain.products.Supply;
import com.infosupport.t2c3.exceptions.ItemNotFoundException;
import com.infosupport.t2c3.repositories.*;
import com.infosupport.t2c3.security.SecurityService;
import com.infosupport.t2c3.service.OrderService;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Windows 7 on 20-1-2016.
 */
@Component
public class Initializer {

    private static final Logger logger = LogManager.getLogger(Initializer.class.getSimpleName());

    //Product constants
    private static final int DEFAULT_SUPPLY = 15;

    //Order constants
    private static final int MAX_FIVE = 5;
    private static final int MAX_FOUR = 4;
    private static final int MAX_THREE = 3;

    //Customer constants
    public static final BigDecimal TEST_CREDIT_LIMIT = new BigDecimal(500);

    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private SecurityService securityService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private SupplyRepository supplyRepo;



    @PostConstruct
    public void init() {
        //Creating products
        initProducts();
        //Creating credentials
        initCustomer();
        //Creating orders
        initOrders();
    }

    public void initCustomer() {
        //Creating customer
        Address address = new Address(
                "Dordrecht",
                "Maria Montessorilaan",
                "50",
                "3311 KW"
        );
        Customer cust = new Customer();
        cust.setFirstName("Remco");
        cust.setLastName("Groenenboom");
        cust.setEmailAddress("remco@email.com");
        cust.setAddress(address);
        cust.setCreditLimit(TEST_CREDIT_LIMIT);
        cust.setCredentials(securityService.createCredentials("remco", "password"));
        customerRepo.save(cust);
    }


    public void initProducts() {

            Product p = new Product(
                    "Gazelle fiets",
                    new BigDecimal(499.99),
                    Category.BICYCLE,
                    null
            );

            Supply supply = new Supply(p, DEFAULT_SUPPLY, "Gazzelle");
            supplyRepo.save(supply);

            p = new Product(
                    "Batavus fiets",
                    new BigDecimal(699.99),
                    Category.BICYCLE,
                    null
            );

            supply = new Supply(p, DEFAULT_SUPPLY, "Batavus");
            supplyRepo.save(supply);

            p = new Product(
                    "Handrem",
                    new BigDecimal(18.99),
                    Category.PART,
                    null
            );

            supply = new Supply(p, DEFAULT_SUPPLY, "Gazzelle");
            supplyRepo.save(supply);

            p = new Product(
                    "Achterlicht",
                    new BigDecimal(6.99),
                    Category.PART,
                    null
            );

            supply = new Supply(p, DEFAULT_SUPPLY, "Batavus");
            supplyRepo.save(supply);

            p = new Product(
                    "Handvaten",
                    new BigDecimal(14.75),
                    Category.PART,
                    null
            );

            supply = new Supply(p, DEFAULT_SUPPLY, "Gazzelle");
            supplyRepo.save(supply);
        }


    private void initOrders() {
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < 2; i++) {
            List<OrderItem> items = new ArrayList<>();
            for (int a = 0; a < MAX_THREE; a++) {
                items.add(
                        OrderItem.builder()
                                .amount(random.nextInt(MAX_FOUR) + 1)
                                .product(productRepo.findOne((long) random.nextInt(MAX_FIVE) + 1))
                                .build()
                );
            }

            Order order = new Order(
                    null,
                    OrderStatus.PLACED,
                    false,
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

            try {
                orderService.calculatePrices(order);
            } catch (ItemNotFoundException e) {
                logger.fatal("This should be impossible", e);
            }

            orderRepo.save(order);
        }

        Customer customer = customerRepo.findOne(1l);
        customer.addOrder(orderRepo.findOne(1l));
        customerRepo.save(customer);
    }

}
