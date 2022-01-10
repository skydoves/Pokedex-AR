
package com.skydoves.pokedexar.ui.shop

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.amn.easysharedpreferences.EasySharedPreference
import com.google.android.filament.Box
import com.skydoves.bindables.BindingActivity
import com.skydoves.bundler.intentOf
import com.skydoves.pokedexar.R
import com.skydoves.pokedexar.database.BoxData
import com.skydoves.pokedexar.database.BoxListService
import com.skydoves.pokedexar.database.GachaService
import com.skydoves.pokedexar.databinding.ActivitySceneBinding
import com.skydoves.pokedexar.extensions.applyFullScreenWindow
import com.skydoves.pokedexar.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@AndroidEntryPoint
class ShopActivity : BindingActivity<ActivitySceneBinding>(R.layout.activity_shop) {

  private val viewModel by viewModels<ShopViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    applyFullScreenWindow()
    super.onCreate(savedInstanceState)

    val draw_btn = findViewById<Button>(R.id.draw_btn)



    draw_btn.setOnClickListener {
      val retrofit = Retrofit.Builder().baseUrl("http://192.249.18.193:80")
        .addConverterFactory(GsonConverterFactory.create()).build()
      val service = retrofit.create(GachaService::class.java)
      service.requestBoxList( "Token ${EasySharedPreference.Companion.getString("token", "noToken")}" ).enqueue(
        object : Callback<BoxData> {
          override fun onFailure(call: Call<BoxData>, t: Throwable) {

          }

          override fun onResponse(call: Call<BoxData>, response: Response<BoxData>) {
            Toast.makeText(this@ShopActivity , response.body()?.pokemon?.name +"를 뽑았다!", Toast.LENGTH_SHORT).show()
          }
        }
      )

    }

  }

  companion object {
    fun startActivity(context: Context) {
      context.intentOf<ShopActivity> {
        startActivity(context)
      }
    }
  }
}
