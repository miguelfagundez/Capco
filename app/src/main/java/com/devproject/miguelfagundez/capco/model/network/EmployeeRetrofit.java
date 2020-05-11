package com.devproject.miguelfagundez.capco.model.network;

import com.devproject.miguelfagundez.capco.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/********************************************
 * Class- EmployeeRetrofit
 * Create the retrofit service using a
 * singleton pattern
 * @author: Miguel Fagundez
 * @date: May 09th, 2020
 * @version: 1.0
 * *******************************************/
public class EmployeeRetrofit {

    //*******************************
    // Singleton pattern
    //*******************************
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitClient(){
        if (retrofit == null){
            synchronized (EmployeeRetrofit.class){
                if (retrofit == null){
                    retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }
}

