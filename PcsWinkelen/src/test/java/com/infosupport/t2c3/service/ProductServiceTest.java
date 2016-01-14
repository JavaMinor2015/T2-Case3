package com.infosupport.t2c3.service;

import com.infosupport.t2c3.Config;
import com.infosupport.t2c3.domain.products.Category;
import com.infosupport.t2c3.domain.products.Product;
import com.infosupport.t2c3.repositories.ProductRepository;
import com.infosupport.t2c3.test.MockRepository;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by Stoux on 13/01/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Config.class, TestContext.class})
@WebAppConfiguration
public class ProductServiceTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ProductRepository productRepo;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(this.webApplicationContext).build();

        //Create products
        productRepo.deleteAll();
        for (int i = 1; i <= 3; i++) {
            Product p = new Product("P" + i, new BigDecimal(i + "," + i), Category.BICYCLE, "Supply" + i, true, null);
            productRepo.save(p);
        }
    }

    @Test
    public void testGetById() throws Exception {
        this.mockMvc.perform(
                get("/products").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productList.length()").value(3));
    }

    @Bean
    public ProductRepository getProductRepo() {
        MockedProductRepository repo = Mockito.mock(MockedProductRepository.class);
        MockRepository.mockInit(repo, Product.class);
        return repo;
    }

    /**
     * A mocked variant of a ProductRepository.
     */
    private abstract class MockedProductRepository extends MockRepository<Product> implements ProductRepository {

    }

}