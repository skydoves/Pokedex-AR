package com.skydoves.pokedexar.database

import com.google.android.filament.Box
import retrofit2.Call
import retrofit2.http.*

interface BoxListService{

    @GET("/pokemon/api/box/")
    fun requestBoxList(
        @Header("Authorization") token: String,
    ) : Call<Array<BoxData>>

    @DELETE("/pokemon/api/box/{id}")
    fun deleteBox(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ) : Call<String>
}