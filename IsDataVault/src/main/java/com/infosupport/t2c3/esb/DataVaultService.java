package com.infosupport.t2c3.esb;

import com.infosupport.t2c3.domain.abs.DataVaultEnabled;
import com.infosupport.t2c3.retrofit.DataVaultModel;
import com.infosupport.t2c3.retrofit.EsbEndpoint;
import java.util.ArrayList;
import java.util.List;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import retrofit2.Call;

/**
 * Created by Stoux on 18/01/2016.
 */
@Component
public class DataVaultService {

    @Autowired
    private EsbEndpoint esbEndpoint;

    /**
     * Store some data in the DataVault using the ESB.
     *
     * @param method The method
     * @param mainObject The main object
     * @param additionalObjects Any additional objects
     */
    public void store(String method, DataVaultEnabled mainObject, DataVaultEnabled... additionalObjects) {
        //Convert to model
        List<String> additionalKeys = new ArrayList<>();
        for (DataVaultEnabled object : additionalObjects) {
            additionalKeys.add(object.getBusinessKey());
        }
        DataVaultModel model = new DataVaultModel(
                mainObject.getBusinessKey(),
                additionalKeys
        );

        //Post to ESB
        Call<Void> call = esbEndpoint.storeData(method, model);
        call.enqueue(null);
    }

    /**
     * Generate an EsbEndpoint.
     *
     * @return the endpoint
     */
    @Bean
    public static EsbEndpoint generateEsbEndpoint() {
        //TODO: Replace with Actual RetroFit implementation
        final Call mockedCall = Mockito.mock(Call.class);
        return (method, vaultModel) -> mockedCall;
    }

}
