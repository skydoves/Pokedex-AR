package com.skydoves.pokedexar.ui.login

import com.google.gson.annotations.SerializedName

data class Login(
    @SerializedName("Token")
    val token: String
)