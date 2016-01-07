package com.infosupport.t2c3.service;

import com.infosupport.t2c3.data.BasicRepository;
import com.infosupport.t2c3.domain.products.Category;
import com.infosupport.t2c3.domain.products.Product;
import com.infosupport.t2c3.repositories.ProductRepository;
import java.math.BigDecimal;
import java.security.SecureRandom;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Windows 7 on 6-1-2016.
 */
@RestController
@RequestMapping(value = "/products", produces = "application/json")
public class ProductService extends AbsRestService<Product> {

    private static final int ADD_PRODUCTS = 15;

    @Autowired
    private ProductRepository productRepo;

    @Override
    public BasicRepository<Product> provideRepo() {
        return productRepo;
    }

    /**
     * Initialize this product service.
     */
    @PostConstruct
    public void init() {
        //TODO: Remove this, is just adding random data
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < ADD_PRODUCTS; i++) {
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
