package com.skydoves.pokedexar.ui.login

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginService{

    @FormUrlEncoded
    @POST("/user/login/")
    fun requestLogin(
        //@Header("Authorization") token: String,
        @Field("id") userid:String,
        @Field("password") userpw:String
    ) : Call<Login>

}