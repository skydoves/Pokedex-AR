
package com.skydoves.pokedexar.ui.shop

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.activity.viewModels
import com.amn.easysharedpreferences.EasySharedPreference
import com.google.android.filament.Box
import com.skydoves.bindables.BindingActivity
import com.skydoves.bundler.intentOf
import com.skydoves.pokedexar.R
import com.skydoves.pokedexar.database.BoxData
import com.skydoves.pokedexar.database.BoxListService
import com.skydoves.pokedexar.database.DataIO
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

    val draw_btn = findViewById<ImageButton>(R.id.gacha1)

    val shineEffect = findViewById<ImageView>(R.id.shine_effect)
    ObjectAnimator.ofFloat(shineEffect, "rotation", 180f).apply{
      duration = 3000
      repeatCount = ValueAnimator.INFINITE
      start()
    }

    draw_btn.setOnClickListener {
      DataIO.updateUserAndDo(10000){
        println(it)
        println(it.message)
        val msg = it.message
        if(msg == "success"){
          DataIO.gachaAndDo {
            Toast.makeText(this@ShopActivity ,it.pokemon.name +"를 뽑았다!", Toast.LENGTH_SHORT).show()
          }
        } else {
          Toast.makeText(this@ShopActivity ,"돈이 부족합니다", Toast.LENGTH_SHORT).show()
        }
      }

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
