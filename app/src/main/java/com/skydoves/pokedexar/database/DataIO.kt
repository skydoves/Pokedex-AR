package com.skydoves.pokedexar.database

import android.app.Dialog
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
        fun selectBoxAndDo(boxId: Int, select:Int, action:() -> Unit ){
            val retrofit = Retrofit.Builder().baseUrl("http://192.249.18.193:80")
                .addConverterFactory(GsonConverterFactory.create()).build()
            val service = retrofit.create(BoxListService::class.java)
            service.updateBox( "Token ${EasySharedPreference.Companion.getString("token", "noToken")}", boxId, select).enqueue(
                object : Callback<String> {
                    override fun onFailure(call: Call<String>, t: Throwable) {

                    }
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        action()
                    }
                }
            )
        }

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

        fun requestBoxAndDo(action: (Array<BoxData>) -> Unit){
            val retrofit = Retrofit.Builder().baseUrl("http://192.249.18.193:80")
                .addConverterFactory(GsonConverterFactory.create()).build()
            val service = retrofit.create(BoxListService::class.java)
            service.requestBoxList( "Token ${EasySharedPreference.Companion.getString("token", "noToken")}" ).enqueue(
                object : Callback<Array<BoxData>> {
                    override fun onFailure(call: Call<Array<BoxData>>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<Array<BoxData>>, response: Response<Array<BoxData>>) {
                        try {
                            val arr: Array<BoxData> = response.body()!!
                            action(arr)
                        } catch(e:Exception){

                        }

                    }
                }
            )
        }

        fun requestUserAndDo(action: (UserData) -> Unit){
            val retrofit = Retrofit.Builder().baseUrl("http://192.249.18.193:80")
                .addConverterFactory(GsonConverterFactory.create()).build()
            val service = retrofit.create(UserService::class.java)
            service.requestUser("Token ${EasySharedPreference.Companion.getString("token", "noToken")}").enqueue(
                object : Callback<UserData> {
                    override fun onFailure(call: Call<UserData>, t: Throwable) {

                    }
                    override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                        try {
                            val user:UserData = response.body()!!
                            action(user)
                        } catch(e:Exception){

                        }
                    }
                }
            )
        }

        fun updateUserAndDo(price:Int, action: (MSG) -> Unit){
            val retrofit = Retrofit.Builder().baseUrl("http://192.249.18.193:80")
                .addConverterFactory(GsonConverterFactory.create()).build()
            val service = retrofit.create(UserService::class.java)
            service.updateUser("Token ${EasySharedPreference.Companion.getString("token", "noToken")}", price).enqueue(
                object : Callback<MSG> {
                    override fun onFailure(call: Call<MSG>, t: Throwable) {

                    }
                    override fun onResponse(call: Call<MSG>, response: Response<MSG>) {
                        try {
                            val str:MSG = response.body()!!
                            action(str)
                        } catch(e:Exception){
                            println(e.toString())
                        }
                    }
                }
            )
        }

        fun gachaAndDo(action: (BoxData) -> Unit){
            val retrofit = Retrofit.Builder().baseUrl("http://192.249.18.193:80")
                .addConverterFactory(GsonConverterFactory.create()).build()
            val service = retrofit.create(GachaService::class.java)
            service.requestBoxList( "Token ${EasySharedPreference.Companion.getString("token", "noToken")}" ).enqueue(
                object : Callback<BoxData> {
                    override fun onFailure(call: Call<BoxData>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<BoxData>, response: Response<BoxData>) {
                        try {
                            val tmp:BoxData = response.body()!!
                            action(tmp)
                        } catch(e:Exception){

                        }
                    }
                }
            )
        }

        fun requestSelectedBoxAndDo(action: (Array<BoxData?>) -> Unit){
            requestBoxAndDo {
                var selectedArray = arrayOf<BoxData?>(null,null,null,null)
                it.forEach {
                    if(it.selected > 0 ){
                        selectedArray[it.selected-1] = it
                    }
                }
                action(selectedArray)
            }
        }
    }
}