package com.infosupport.t2c3.repositories;

import com.infosupport.t2c3.data.BasicRepository;
import com.infosupport.t2c3.domain.products.Product;
import org.springframework.stereotype.Component;

/**
 * Created by Stoux on 12/01/2016.
 */
@Component
public interface SupplyRepository extends BasicRepository<Product> {

}
