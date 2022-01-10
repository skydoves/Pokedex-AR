package com.skydoves.pokedexar.ui.login

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface SignupService{

    @FormUrlEncoded
    @POST("/user/signup/")
    fun requestSignup(
        //@Header("Authorization") token: String,
        @Field("id") userid:String,
        @Field("name") username:String,
        @Field("email") email:String,
        @Field("password") userpw:String,
    ) : Call<Signup>

}