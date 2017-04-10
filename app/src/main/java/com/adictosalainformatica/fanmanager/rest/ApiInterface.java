package com.adictosalainformatica.fanmanager.rest;

import com.adictosalainformatica.fanmanager.model.FanModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ruben on 17/09/16.
 */

public interface ApiInterface {
    @GET("digital/{pin}/{value}")
    Call<FanModel> setPin(@Path("pin") int pin, @Path("value") int value);

    @GET("status/{pin}")
    Call<FanModel> getStatus(@Path("pin") int pin);
}
