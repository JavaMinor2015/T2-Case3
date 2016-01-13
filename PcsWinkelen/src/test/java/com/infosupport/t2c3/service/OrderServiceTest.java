package com.infosupport.t2c3.service;

import com.infosupport.t2c3.domain.customers.Customer;
import com.infosupport.t2c3.domain.orders.Order;
import com.infosupport.t2c3.domain.orders.OrderItem;
import com.infosupport.t2c3.domain.products.Product;
import com.infosupport.t2c3.model.OrderRequest;
import com.infosupport.t2c3.model.Token;
import com.infosupport.t2c3.repositories.CustomerRepository;
import com.infosupport.t2c3.repositories.OrderRepository;
import com.infosupport.t2c3.repositories.ProductRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Windows 7 on 11-1-2016.
 */
public class OrderServiceTest extends TestCase {

    private Order order;
    private Customer customer;
    private List<OrderItem> items;
    private OrderService orderService;
    private OrderRepository mockedOrderRepo;
    private ProductRepository mockedProductRepo;
    private CustomerRepository mockedCustomerRepo;

    public void setUp() throws Exception {
        super.setUp();
        Product p1 = mock(Product.class);
        when(p1.getPrice()).thenReturn(new BigDecimal(173.22));
        Product p2 = mock(Product.class);
        when(p2.getPrice()).thenReturn(new BigDecimal(20.57));
        Product p3 = mock(Product.class);
        when(p3.getPrice()).thenReturn(new BigDecimal(0.89));

        customer = new Customer();
        customer.setFirstName("testVoornaam");
        customer.setOrders(new ArrayList());

        OrderItem item1 = new OrderItem(null, 2, p1);
        OrderItem item2 = new OrderItem(null, 3, p2);
        OrderItem item3 = new OrderItem(null, 1, p3);

        items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        items.add(item3);

        order = new Order(null, null, items, null);

        orderService = new OrderService();
        mockedOrderRepo = mock(OrderRepository.class);
        mockedProductRepo = mock(ProductRepository.class);
        mockedCustomerRepo = mock(CustomerRepository.class);

        when(p1.getId()).thenReturn(1l);
        when(p2.getId()).thenReturn(2l);
        when(p3.getId()).thenReturn(3l);

        when(mockedProductRepo.findOne(1l)).thenReturn(p1);
        when(mockedProductRepo.findOne(2l)).thenReturn(p2);
        when(mockedProductRepo.findOne(3l)).thenReturn(p3);

        when(mockedCustomerRepo.findByCredentialsToken("TOKENFORCUSTOMER"))
                .thenReturn(customer);

        orderService.setOrderRepo(mockedOrderRepo);
        orderService.setProductRepo(mockedProductRepo);
        orderService.setCustomerRepo(mockedCustomerRepo);

    }

    public void testPlaceOrderWithToken(){
        assertTrue(customer.getOrders().size() == 0);
        orderService.placeOrder(new OrderRequest(new Token("TOKENFORCUSTOMER", null), order));
        assertTrue(customer.getOrders().size() == 1 );
    }

    public void testPlaceOrderWithoutToken(){

        assertTrue(customer.getOrders().size() == 0);
        orderService.placeOrder(new OrderRequest(null, order));
        assertTrue(customer.getOrders().size() == 0 );
    }

    public void testCalculatePrices(){
        assertEquals(null, order.getTotalPrice());
        assertEquals(null, order.getItems().get(0).getPrice());
        assertEquals(null, order.getItems().get(1).getPrice());
        assertEquals(null, order.getItems().get(2).getPrice());

        Product p1 = mockedProductRepo.findOne(items.get(0).getProduct().getId());
        Product p2 = mockedProductRepo.findOne(items.get(1).getProduct().getId());

        orderService.placeOrder(new OrderRequest(null, order));

        assertEquals(new BigDecimal(173.22).setScale(2, RoundingMode.HALF_UP), order.getItems().get(0).getPrice().setScale(2, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal(20.57).setScale(2, RoundingMode.HALF_UP), order.getItems().get(1).getPrice().setScale(2, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal(0.89).setScale(2, RoundingMode.HALF_UP), order.getItems().get(2).getPrice().setScale(2, RoundingMode.HALF_UP));
        assertEquals(new BigDecimal(409.04).setScale(2, RoundingMode.HALF_UP), order.getTotalPrice().setScale(2, RoundingMode.HALF_UP));
    }
}