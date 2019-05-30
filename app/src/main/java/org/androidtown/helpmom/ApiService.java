package org.androidtown.helpmom;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {


    //회원가입
    @FormUrlEncoded
    @POST("register")
    Call<RegisterResult> getRegister(@Field("id") String id,
                           @Field("pwd") String pwd, @Field("name") String name);

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

    @FormUrlEncoded
    @POST("joinRoom")
    Call<RegisterResult> getJoin(@Field("id") String id, @Field("name") String name, @Field("pwd") String pwd,@Field("teamMate") String teamMate);

    @FormUrlEncoded
    @POST("memberList")
    Call<RegisterResult> getMember(@Field("roomNumber") String roomNumber);

    @FormUrlEncoded
    @POST("getTask")
    Call<RegisterResult> getTask(@Field("roomNumber")String roomNumber);

    @FormUrlEncoded
    @POST("createTask")
    Call<RegisterResult> createTask(@Field("roomNumber") String roomNumber, @Field("task") ArrayList<String> task);


    @FormUrlEncoded
    @POST("evaluate")
    Call<RegisterResult> evaluate(@Field("taskId") String taskId, @Field("comment") String comment,@Field("point") String point);
}
