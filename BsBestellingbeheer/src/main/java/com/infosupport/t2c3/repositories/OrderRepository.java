package com.infosupport.t2c3.repositories;

import com.infosupport.t2c3.data.BasicRepository;
import com.infosupport.t2c3.domain.orders.Order;
import org.springframework.stereotype.Component;

/**
 * Created by Windows 7 on 06/01/2016.
 */
@Component
public interface OrderRepository extends BasicRepository<Order> {

}
