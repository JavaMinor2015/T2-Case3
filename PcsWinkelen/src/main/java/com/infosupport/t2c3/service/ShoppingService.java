package com.infosupport.t2c3.service;

import com.infosupport.t2c3.domain.products.Category;
import com.infosupport.t2c3.domain.products.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ws.rs.Produces;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Windows 7 on 6-1-2016.
 */
@RestController
@RequestMapping(value = "/products")
public class ShoppingService {

//    TODO replace hardcode list with repository
//    @Autowired
//    private ProductRepository productRepo;

    private List<Product> products;

    @RequestMapping("")
    @Produces("application/json")
    public List<Product> getAllProducts() {
        return products;
    }

    @RequestMapping("/{id}")
    @Produces("application/json")
    public Product getProductById(@PathVariable("id") Long id) {

        //TODO replace when repo is implemented
        for (Product p : products) {
            if (p.getID() == id) {
                return p;
            }
        }
        return null;
    }


    @PostConstruct
    public void init() {
        products = null;
        products = new ArrayList<>();
        products.add(new Product(1, "Fiets 1", new BigDecimal(200), Category.BICYCLE, "Leverancier A", true, ""));
        products.add(new Product(2, "Fiets 2", new BigDecimal(180), Category.BICYCLE, "Leverancier B", true, ""));
        products.add(new Product(3, "Onderdeel 1", new BigDecimal(35), Category.PART, "Leverancier A", true, ""));
        products.add(new Product(4, "Onderdeel 2", new BigDecimal(25), Category.PART, "Leverancier B", true, ""));
    }

}
