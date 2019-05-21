package org.androidtown.helpmom;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {


    @GET("posts")
    Call<List<RegisterResult>> getPosts();

    @FormUrlEncoded
    @POST("register")
    Call<RegisterResult> getRegister(@Field("id") String id,
                           @Field("pwd") String pwd, @Field("name") String name         );

    @FormUrlEncoded
    @POST("login")
    Call<RegisterResult> getLogin(@Field("id") String id,
                        @Field("pwd") String pwd);
}
