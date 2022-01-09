package com.skydoves.pokedexar.ui.main

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.GridView
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import com.amn.easysharedpreferences.EasySharedPreference
import com.skydoves.bindables.BindingActivity
import com.skydoves.pokedexar.R
import com.skydoves.pokedexar.databinding.ActivityMainBinding
import com.skydoves.pokedexar.model.Pokemon
import com.skydoves.pokedexar.ui.adapter.PokemonAdapter
import com.skydoves.pokedexar.ui.details.DetailActivity
import com.skydoves.pokedexar.ui.home.GVAdapter
import com.skydoves.pokedexar.ui.home.HomeActivity
import com.skydoves.pokedexar.ui.scene.SceneActivity
import com.skydoves.pokedexar.ui.shop.ShopActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

  @VisibleForTesting
  val viewModel: MainViewModel by viewModels()

  lateinit var dialog02 :Dialog

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    dialog02 = Dialog(this)

    //println("Hi Friends")
    //println(EasySharedPreference.Companion.getString("token", "noToken"))

    binding {
      lifecycleOwner = this@MainActivity
      adapter = PokemonAdapter()
      vm = viewModel
      HomeBtn.setOnClickListener {
        HomeActivity.startActivity(this@MainActivity)
      }
      BattleBtn.setOnClickListener {
        showDialog02()
      }
      ShopBtn.setOnClickListener {
        ShopActivity.startActivity(this@MainActivity)
      }
    }
  }

  fun showDialog02(){
    dialog02.setContentView(R.layout.battleroom_dialog)
    dialog02.show()

    val enter_btn = dialog02.findViewById<Button>(R.id.enter_btn)
    enter_btn.setOnClickListener{
      SceneActivity.startActivity(this@MainActivity)
    }

    val cancel_btn = dialog02.findViewById<Button>(R.id.cancel_btn)
    cancel_btn.setOnClickListener {
      dialog02.dismiss()
    }


  }
}
