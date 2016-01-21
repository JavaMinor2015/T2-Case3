package com.infosupport.t2c3.service;

import com.infosupport.t2c3.data.BasicRepository;
import com.infosupport.t2c3.domain.products.Product;
import com.infosupport.t2c3.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Windows 7 on 6-1-2016.
 */
@RestController
@RequestMapping(value = "/products", produces = "application/json")
public class ProductService extends AbsRestService<Product> {

    @Autowired
    private ProductRepository productRepo;

    @Override
    public BasicRepository<Product> provideRepo() {
        return productRepo;
    }

}
