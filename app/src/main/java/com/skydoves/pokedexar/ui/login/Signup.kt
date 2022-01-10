package com.skydoves.pokedexar.ui.login

import com.google.gson.annotations.SerializedName

data class Signup(
    @SerializedName("Success")
    val success: Int,

    @SerializedName("Message")
    val message: String,
)