package com.skydoves.pokedexar.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import com.skydoves.bindables.BindingActivity
import com.skydoves.pokedexar.R
import com.skydoves.pokedexar.databinding.ActivityMainBinding
import com.skydoves.pokedexar.model.Pokemon
import com.skydoves.pokedexar.ui.adapter.PokemonAdapter
import com.skydoves.pokedexar.ui.details.DetailActivity
import com.skydoves.pokedexar.ui.home.HomeActivity
import com.skydoves.pokedexar.ui.scene.SceneActivity
import com.skydoves.pokedexar.ui.shop.ShopActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

  @VisibleForTesting
  val viewModel: MainViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding {
      lifecycleOwner = this@MainActivity
      adapter = PokemonAdapter()
      vm = viewModel
      HomeBtn.setOnClickListener {
        HomeActivity.startActivity(this@MainActivity)
      }
      BattleBtn.setOnClickListener {
        SceneActivity.startActivity(this@MainActivity)
      }
      ShopBtn.setOnClickListener {
        ShopActivity.startActivity(this@MainActivity)
      }
    }
  }
}
