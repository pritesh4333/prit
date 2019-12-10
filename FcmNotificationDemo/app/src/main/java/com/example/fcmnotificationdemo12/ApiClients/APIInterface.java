package com.example.fcmnotificationdemo12.ApiClients;


import com.example.fcmnotificationdemo12.model.UserList;
import com.example.fcmnotificationdemo12.model.Warehouse;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by anupamchugh on 09/01/17.
 */

public interface APIInterface {

    @GET("/api/users?")
    Call<UserList> doGetUserList(@Query("page") String page);

    @GET("/api/warehous/{id}")
    Call<Warehouse> getWarehouseDetail(@Path("id") String id, @Header("Token") String Authorization, @Header("UserName") String UserName, @Header("ClientToken") String ClientToken);
}
