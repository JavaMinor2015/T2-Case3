package com.infosupport.t2c3.retrofit;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Stoux on 18/01/2016.
 */
@Getter
@AllArgsConstructor
public class DataVaultModel {

    private String mainKey;
    private List<String> additionalKeys;

}
