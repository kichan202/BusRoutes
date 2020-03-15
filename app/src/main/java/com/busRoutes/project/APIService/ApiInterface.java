package com.busRoutes.project.APIService;

import com.busRoutes.project.model.Response;
import com.busRoutes.project.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("register")
    Call<Response> registerUser(@Body User user);

    @POST("login")
    Call<ResponseBody> loginUser(@Body User user);

}
