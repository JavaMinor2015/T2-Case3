package com.infosupport.t2c3.repositories;

import com.infosupport.t2c3.domain.abs.AbsEntity;
import com.infosupport.t2c3.domain.products.Category;
import com.infosupport.t2c3.domain.products.Product;
import com.infosupport.t2c3.domain.products.Supply;
import com.infosupport.t2c3.exceptions.ItemNotFoundException;
import com.infosupport.t2c3.exceptions.NoSupplyException;
import java.math.BigDecimal;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Stoux on 21/01/2016.
 */
public class SupplyHandlerTest {

    public static final int DEFAULT_LEFT = 15;
    private SupplyRepository mockedRepo;
    private SupplyHandler handler;

    @Before
    public void setUp() throws Exception {
        mockedRepo = Mockito.mock(SupplyRepository.class);
        handler = new SupplyHandler();
        ReflectionTestUtils.setField(handler, "repo", mockedRepo);
    }

    @Test
    public void testNoProductFound() throws Exception {
        //Setup
        returnForProduct(any(), null);
        Product nonExistingProduct = new Product("A", null, null, null);

        //Execute
        try {
            handler.getUnitsLeft(nonExistingProduct);
            fail();
        } catch (ItemNotFoundException e) {
            //Test succeeded
        }
    }

    @Test
    public void testGetUnitsLeft() throws Exception {
        //Setup
        Supply supply = supplyItem("A", 1L);
        returnForProduct(supply.getProduct(), supply);

        //Execute
        int unitsLeft = handler.getUnitsLeft(supply.getProduct());

        //Assert
        assertEquals(DEFAULT_LEFT, unitsLeft);
    }

    @Test
    public void testDecreaseStock() throws Exception {
        //Setup
        Supply supply = supplyItem("A", 1L);
        returnForProduct(supply.getProduct(), supply);

        //Execute
        int unitsLeft = handler.decreaseStock(supply.getProduct(), 10);

        //Assert
        verifySupply(DEFAULT_LEFT - 10, unitsLeft, supply);
    }

    @Test
    public void testOutOfStock() throws Exception {
        //Setup
        Supply supply = supplyItem("A", 1L);
        returnForProduct(supply.getProduct(), supply);

        //Execute
        try {
            handler.decreaseStock(supply.getProduct(), DEFAULT_LEFT + 1);
            fail();
        } catch (NoSupplyException e) {
            assertEquals(DEFAULT_LEFT, e.getItemsLeft());
        }
    }

    @Test
    public void testIncreaseStock() throws Exception {
        //Setup
        Supply supply = supplyItem("A", 1L);
        returnForProduct(supply.getProduct(), supply);

        //Execute
        int unitsLeft = handler.increaseStock(supply.getProduct(), 10);

        //Assert
        verifySupply(DEFAULT_LEFT + 10, unitsLeft, supply);
    }

    private Supply supplyItem(String name, Long id) {
        Product p = new Product(name, BigDecimal.TEN, Category.BICYCLE, null);
        setId(p, id);
        Supply s = new Supply(p, DEFAULT_LEFT, "RandomSupplier");
        setId(s, id);
        return s;
    }

    private void setId(AbsEntity entity, Long id) {
        ReflectionTestUtils.setField(entity, "id", id);
    }

    private void returnForProduct(Product product, Supply supply) {
        when(mockedRepo.findByProduct(product)).thenReturn(supply);
    }

    private void verifySupply(int expected, int result, Supply supply) {
        assertEquals(expected, result);
        assertEquals(expected, supply.getLeft());
        verify(mockedRepo, times(1)).save(supply);
    }

}