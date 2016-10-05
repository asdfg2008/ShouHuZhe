package com.crg.shouhuzhe.netutils;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by crg on 16/10/5.
 */

public interface SHZRegister {
    @FormUrlEncoded
    @POST("register.php")
    Call<String> register(@Field("username") String username,
                          @Field("password") String password);
}
