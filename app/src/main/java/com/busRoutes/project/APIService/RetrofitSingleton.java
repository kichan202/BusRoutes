/*this will be use to make sure only one
* Retrofit client is there at any moment*/

package com.busRoutes.project.APIService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {

    private static Retrofit sRetrofit = null;

    public static Retrofit getRetrofit(String baseURL){
        if(sRetrofit == null){
            sRetrofit = new Retrofit.Builder().baseUrl(baseURL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return sRetrofit;
    }
}
