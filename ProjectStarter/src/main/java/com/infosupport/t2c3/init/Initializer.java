package com.infosupport.t2c3.init;

import com.infosupport.t2c3.domain.accounts.Customer;
import com.infosupport.t2c3.domain.orders.*;
import com.infosupport.t2c3.domain.products.Category;
import com.infosupport.t2c3.domain.products.Product;
import com.infosupport.t2c3.domain.products.Supply;
import com.infosupport.t2c3.exceptions.ItemNotFoundException;
import com.infosupport.t2c3.repositories.CustomerRepository;
import com.infosupport.t2c3.repositories.OrderRepository;
import com.infosupport.t2c3.repositories.ProductRepository;
import com.infosupport.t2c3.repositories.SupplyRepository;
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
    private static final String GAZELLE = "Gazelle";
    private static final String BATAVUS = "Batavus";
    private static final double GAZELLE_BIKE_PRICE = 499.99;
    private static final double BATAVUS_BIKE_PRICE = 699.99;
    private static final double HANDBRAKE_PRICE = 18.99;
    private static final double BACKLIGHT_PRICE = 6.99;
    private static final double HANDLE_PRICE = 14.75;

    //Order constants
    private static final int MAX_FIVE = 5;
    private static final int MAX_FOUR = 4;
    private static final int MAX_THREE = 3;

    //Customer constants
    public static final BigDecimal TEST_CREDIT_LIMIT = BigDecimal.valueOf(500);

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


    /**
     * Calls the dummy creating methods.
     */
    @PostConstruct
    public void init() {
        //Creating products
        initProducts();
        //Creating credentials
        initCustomer();
        //Creating orders
        initOrders();
    }

    /**
     * Creates the dummy customers.
     */
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


    /**
     * Creates the dummy products.
     */
    public void initProducts() {

        Product p = new Product(
                GAZELLE + " fiets",
                BigDecimal.valueOf(GAZELLE_BIKE_PRICE),
                Category.BICYCLE,
                null
        );

        Supply supply = new Supply(p, DEFAULT_SUPPLY, GAZELLE);
        supplyRepo.save(supply);

        p = new Product(
                BATAVUS + " fiets",
                BigDecimal.valueOf(BATAVUS_BIKE_PRICE),
                Category.BICYCLE,
                null
        );

        supply = new Supply(p, DEFAULT_SUPPLY, BATAVUS);
        supplyRepo.save(supply);

        p = new Product(
                "Handrem",
                BigDecimal.valueOf(HANDBRAKE_PRICE),
                Category.PART,
                null
        );

        supply = new Supply(p, DEFAULT_SUPPLY, GAZELLE);
        supplyRepo.save(supply);

        p = new Product(
                "Achterlicht",
                BigDecimal.valueOf(BACKLIGHT_PRICE),
                Category.PART,
                null
        );

        supply = new Supply(p, DEFAULT_SUPPLY, BATAVUS);
        supplyRepo.save(supply);

        p = new Product(
                "Handvaten",
                BigDecimal.valueOf(HANDLE_PRICE),
                Category.PART,
                null
        );

        supply = new Supply(p, DEFAULT_SUPPLY, GAZELLE);
        supplyRepo.save(supply);
    }


    /**
     * Creates dummy orders.
     */
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

        Customer customer = customerRepo.findOne(1L);
        customer.addOrder(orderRepo.findOne(1L));
        customerRepo.save(customer);
    }

}
