package com.crg.shouhuzhe.netutils;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by crg on 16/10/4.
 */

/**
 * 提供的请求方式注解有@GET 和@POST
 * 参数注解有@PATH  和@Query
 * */

public interface RequestServers {
    @FormUrlEncoded
    @POST("login.php")
    Call<String> login(@Field("username") String username,
                           @Field("password") String password);
}
