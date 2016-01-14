package com.infosupport.t2c3.repositories;

import com.infosupport.t2c3.data.BasicRepository;
import com.infosupport.t2c3.domain.products.Product;
import com.infosupport.t2c3.domain.products.Supply;
import org.springframework.stereotype.Component;

/**
 * Created by Stoux on 12/01/2016.
 */
@Component
public interface SupplyRepository extends BasicRepository<Supply> {

    /**
     * Find the current Supply of a certain Product.
     *
     * @param product The product
     * @return The supply or null if not found
     */
    Supply findByProduct(Product product);

}
