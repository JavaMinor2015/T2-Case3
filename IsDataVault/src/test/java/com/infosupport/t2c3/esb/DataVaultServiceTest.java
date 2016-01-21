package com.infosupport.t2c3.esb;

import com.infosupport.t2c3.domain.abs.AbsVaultEntity;
import com.infosupport.t2c3.retrofit.EsbEndpoint;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockingDetails;
import org.springframework.test.util.ReflectionTestUtils;
import retrofit2.Call;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

/**
 * Created by Stoux on 21/01/2016.
 */
public class DataVaultServiceTest {

    private EsbEndpoint esbEndpoint;
    private DataVaultService dataVaultService;

    @Before
    public void setUp() {
        esbEndpoint = mock(EsbEndpoint.class);
        dataVaultService = new DataVaultService();

        ReflectionTestUtils.setField(dataVaultService, "esbEndpoint", esbEndpoint);
    }

    @Test
    public void testStore() throws Exception {
        //Setup
        List<AbsVaultEntity> entities = vaultEntities();
        AbsVaultEntity main = entities.remove(0);
        AbsVaultEntity[] arr = entities.toArray(new AbsVaultEntity[entities.size()]);

        Call call = mock(Call.class);
        when(esbEndpoint.storeData(any(), any())).thenReturn(call);

        //Execute
        dataVaultService.store("TEST-METHOD", main, arr);

        //Assert
        verify(esbEndpoint, times(1)).storeData(eq("TEST-METHOD"), any());
        verify(call, times(1)).enqueue(any());
    }

    @Test
    public void testEsbEndpoint() {
        //Execute
        EsbEndpoint genEndpoint = DataVaultService.generateEsbEndpoint();
        Call<Void> call = genEndpoint.storeData("METHOD", null);

        //Assert
        MockingDetails details = mockingDetails(call);
        assertTrue(details.isMock());
    }

    private List<AbsVaultEntity> vaultEntities() {
        List<AbsVaultEntity> list = new ArrayList<>();
        for (long i = 1; i <= 5; i++) {
            TestVaultEntity vaultEntity = new TestVaultEntity(i);
            assertEquals("TEST-" + i, vaultEntity.getBusinessKey());
            list.add(vaultEntity);
        }
        return list;
    }

    private class TestVaultEntity extends AbsVaultEntity {

        public TestVaultEntity(Long id) {
            ReflectionTestUtils.setField(this, "id", id);
            ReflectionTestUtils.invokeMethod(this, "fillBusinessKey");
        }

        @Override
        protected String generateBusinessKey() {
            return "TEST-" + getId();
        }

    }


}