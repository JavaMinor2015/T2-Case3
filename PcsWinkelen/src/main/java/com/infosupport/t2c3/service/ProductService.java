package com.infosupport.t2c3.service;

import com.infosupport.t2c3.data.BasicRepository;
import com.infosupport.t2c3.domain.products.Category;
import com.infosupport.t2c3.domain.products.Product;
import com.infosupport.t2c3.repositories.ProductRepository;
import java.math.BigDecimal;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ws.rs.Produces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Windows 7 on 6-1-2016.
 */
@RestController
@RequestMapping(value = "/products")
@CrossOrigin
@Produces("application/json")
public class ProductService extends AbsRestService<Product> {

    @Autowired
    private ProductRepository productRepo;

    @Override
    public BasicRepository<Product> provideRepo() {
        return productRepo;
    }

    @PostConstruct
    public void init() {
        //TODO: Remove this, is just adding random data
        Random random = new Random();
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
