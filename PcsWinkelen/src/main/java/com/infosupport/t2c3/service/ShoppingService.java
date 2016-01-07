package com.infosupport.t2c3.service;

import com.infosupport.t2c3.domain.products.Category;
import com.infosupport.t2c3.domain.products.Product;
import com.infosupport.t2c3.repositories.ProductRepository;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ws.rs.Produces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Windows 7 on 6-1-2016.
 */
@RestController
@RequestMapping(value = "/products")
@Produces("application/json")
public class ShoppingService {

    @Autowired
    private ProductRepository productRepo;

    /**
     * Get all the products from repo.
     *
     * @return all products from repo
     */
    @RequestMapping()
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    /**
     * Get product by id.
     * @param id the id
     * @return the product
     */
    @RequestMapping("/{id}")
    public Product getProductById(@PathVariable("id") Long id) {
        return productRepo.findOne(id);
    }

    @PostConstruct
    private void init() {
        //TODO: Remove this, is just adding random data
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 15; i++) {
            productRepo.save(new Product(
                    "Thing #" + random.nextInt(),
                    new BigDecimal(random.nextDouble()),
                    random.nextBoolean() ? Category.BICYCLE : Category.PART,
                    "Meme",
                    true,
                    random.nextBoolean() ? "meme" : null
            ));
        }
    }

}
