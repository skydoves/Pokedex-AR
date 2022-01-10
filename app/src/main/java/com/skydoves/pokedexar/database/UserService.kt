package com.skydoves.pokedexar.database

import com.google.android.filament.Box
import retrofit2.Call
import retrofit2.http.*

interface UserService{

    @GET("/user/profile/")
    fun requestUser(
        @Header("Authorization") token: String,
    ) : Call<UserData>

    @FormUrlEncoded
    @PUT("/user/profile/")
    fun updateUser(
        @Header("Authorization") token: String,
        @Field("price") price: Int,
    ) : Call<MSG>
}