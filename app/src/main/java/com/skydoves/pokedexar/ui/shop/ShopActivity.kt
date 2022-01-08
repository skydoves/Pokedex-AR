
package com.skydoves.pokedexar.ui.shop

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.skydoves.bindables.BindingActivity
import com.skydoves.bundler.intentOf
import com.skydoves.pokedexar.R
import com.skydoves.pokedexar.databinding.ActivitySceneBinding
import com.skydoves.pokedexar.extensions.applyFullScreenWindow
import com.skydoves.pokedexar.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShopActivity : BindingActivity<ActivitySceneBinding>(R.layout.activity_shop) {

  private val viewModel by viewModels<ShopViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    applyFullScreenWindow()
    super.onCreate(savedInstanceState)

    val draw_btn = findViewById<Button>(R.id.draw_btn)

    draw_btn.setOnClickListener {
      Toast.makeText(this, "뽑기 성공!", Toast.LENGTH_SHORT).show()
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
