package com.skydoves.pokedexar.database

import android.app.Dialog
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.amn.easysharedpreferences.EasySharedPreference
import com.skydoves.pokedexar.R
import com.skydoves.pokedexar.ui.home.GVAdapter2
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataIO {
    companion object {
        fun deleteBoxAndDo(boxId: Int, action:() -> Unit ){
            val retrofit = Retrofit.Builder().baseUrl("http://192.249.18.193:80")
                .addConverterFactory(GsonConverterFactory.create()).build()
            val service = retrofit.create(BoxListService::class.java)
            service.deleteBox( "Token ${EasySharedPreference.Companion.getString("token", "noToken")}", boxId ).enqueue(
                object : Callback<String> {
                    override fun onFailure(call: Call<String>, t: Throwable) {

                    }
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        action()
                    }
                }
            )
        }
    }
}