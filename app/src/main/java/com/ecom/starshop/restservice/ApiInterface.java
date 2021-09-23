package com.ecom.starshop.restservice;

import com.ecom.starshop.model.UserListResponse;
import com.ecom.starshop.restservice.response.SignUpResponse;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/** ---------
 * Created by Shailendra Lodhi
 * Visit : https://linktr.ee/wackycodes
 * ---------  */
public interface ApiInterface {

    @FormUrlEncoded // annotation used in POST type requests
    @POST("/retrofit/register.php")     // API's endpoints
    void registration(@Field("name") String name,
                             @Field("email") String email,
                             @Field("password") String password,
                             @Field("logintype") String logintype,
                             Callback<SignUpResponse> callback);


    // API's endpoints
    @GET("/retrofit/getuser.php")
    public void getUsersList(
            Callback<List<UserListResponse>> callback);
}
