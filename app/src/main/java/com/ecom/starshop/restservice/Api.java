package com.ecom.starshop.restservice;

import retrofit.RestAdapter;

/** ---------
 * Created by Shailendra Lodhi
 * Visit : https://linktr.ee/wackycodes
 * ---------  */
public class Api {

    public static ApiInterface getClient() {

        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("http://healthyblackmen.org") //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        ApiInterface api = adapter.create(ApiInterface.class);
        return api;
    }


}
