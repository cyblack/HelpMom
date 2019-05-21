package org.androidtown.helpmom;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {


    //회원가입
    @FormUrlEncoded
    @POST("register")
    Call<RegisterResult> getRegister(@Field("id") String id,
                           @Field("pwd") String pwd, @Field("name") String name         );

    //방생성
    @FormUrlEncoded
    @POST("createRoom")
    Call<RegisterResult> getRoom(@Field("name") String name,
                                  @Field("pwd") String pwd,
                                 @Field("maker") String maker);


    //로그인
    @FormUrlEncoded
    @POST("login")
    Call<RegisterResult> getLogin(@Field("id") String id,
                        @Field("pwd") String pwd);

    //리스트 룸
    @FormUrlEncoded
    @POST("listRoom")
    Call<RegisterResult> getListRoom(@Field("id") String id);

}
