package com.infosupport.t2c3.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Stoux on 18/01/2016.
 */
public interface EsbEndpoint {

    /**
     * Store data in the DataStore using the ESB.
     *
     * @param method the method
     * @param vaultModel DataVault data
     * @return a RetroFit call
     */
    @FormUrlEncoded
    @POST("storeData/{method}")
    Call<Void> storeData(@Path("method") String method, @Body DataVaultModel vaultModel);

}
