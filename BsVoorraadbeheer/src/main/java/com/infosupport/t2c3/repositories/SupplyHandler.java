package com.infosupport.t2c3.repositories;

import com.infosupport.t2c3.domain.products.Product;
import com.infosupport.t2c3.domain.products.Supply;
import com.infosupport.t2c3.exceptions.ItemNotFoundException;
import com.infosupport.t2c3.exceptions.NoSupplyException;
import java.util.Optional;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by Stoux on 13/01/2016.
 */
@Component
public class SupplyHandler {

    @Autowired
    private SupplyRepository repo;

    /**
     * Check how many items are still left for a certain product.
     *
     * @param product The product
     * @return number of units if found
     * @throws ItemNotFoundException if the supply is not found
     */
    public Integer getUnitsLeft(Product product) throws ItemNotFoundException {
        return getSupply(product).getLeft();
    }

    /**
     * Decrease the stock of a certain Product.
     *
     * @param product The product
     * @param decreaseWith Decrease with amount
     * @return The new number of units left
     * @throws ItemNotFoundException If no Supply is found for this Product
     * @throws NoSupplyException If there aren't enough units left to use
     */
    public Integer decreaseStock(Product product, Integer decreaseWith) throws ItemNotFoundException, NoSupplyException {
        Supply supply = getSupply(product);
        if (decreaseWith > supply.getLeft()) {
            throw new NoSupplyException(supply.getLeft());
        }
        supply.setLeft(supply.getLeft() - decreaseWith);
        repo.save(supply);
        return supply.getLeft();
    }

    private Supply getSupply(Product product) throws ItemNotFoundException {
        Supply supply = repo.findByProduct(product);
        if (supply == null) throw new ItemNotFoundException("No supply found for Product: " + product.getName());
        return supply;
    }

}
