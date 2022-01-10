package com.skydoves.pokedexar.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import android.app.Dialog
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.GridView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.appcompat.widget.Toolbar
import com.amn.easysharedpreferences.EasySharedPreference
import com.google.gson.Gson
import com.skydoves.bindables.BindingActivity
import com.skydoves.pokedexar.R
import com.skydoves.pokedexar.database.BoxData
import com.skydoves.pokedexar.database.DataIO
import com.skydoves.pokedexar.databinding.ActivityMainBinding
import com.skydoves.pokedexar.model.Pokemon
import com.skydoves.pokedexar.ui.adapter.PokemonAdapter
import com.skydoves.pokedexar.ui.details.DetailActivity
import com.skydoves.pokedexar.ui.home.GVAdapter
import com.skydoves.pokedexar.ui.home.HomeActivity
import com.skydoves.pokedexar.ui.room.SocketHandler
import com.skydoves.pokedexar.ui.login.LoginActivity
import com.skydoves.pokedexar.ui.scene.SceneActivity
import com.skydoves.pokedexar.ui.shop.ShopActivity
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {

  @VisibleForTesting
  val viewModel: MainViewModel by viewModels()

  lateinit var dialog02 :Dialog

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
    val mToolbar = findViewById<Toolbar>(R.id.main_toolbar)
    setSupportActionBar(mToolbar)
    getSupportActionBar()?.setDisplayShowTitleEnabled(false)

    // 아래처럼 사용하세요!
    DataIO.requestUserAndDo {
      findViewById<TextView>(R.id.main_name).text = "이름 : ${it.nickname}"
      findViewById<TextView>(R.id.main_money).text = "돈 : ${it.money}$"
    }

    dialog02 = Dialog(this)


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

    initSocket()
  }

  fun initSocket() {
    SocketHandler.setSocket()
    SocketHandler.establishConnection()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    super.onCreateOptionsMenu(menu)
    getMenuInflater().inflate(R.menu.logout, menu)
    return true
  }

  // 메뉴 선택시 Barcode 스캔을 진행한다.
  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.logout -> {
        EasySharedPreference.Companion.putString("token", "")
        intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
        return true
      }
      else -> return true
    }
  }
  fun showDialog02(){
    dialog02.setContentView(R.layout.battleroom_dialog)
    dialog02.show()

    val enter_btn = dialog02.findViewById<Button>(R.id.enter_btn)
    enter_btn.setOnClickListener{
      DataIO.requestUserAndDo {
        val jsonObj = JSONObject()
        val userId = it.nickname
        jsonObj.put("id", userId.toString())
        DataIO.requestSelectedBoxAndDo {
          println( Gson().toJson(it) )

          //SceneActivity.startActivity(this@MainActivity)
        }
      }

    }

    val cancel_btn = dialog02.findViewById<Button>(R.id.cancel_btn)
    cancel_btn.setOnClickListener {
      dialog02.dismiss()
    }


  }
}
