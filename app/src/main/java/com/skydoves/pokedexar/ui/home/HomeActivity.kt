package com.skydoves.pokedexar.ui.home

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import com.amn.easysharedpreferences.EasySharedPreference
import com.skydoves.bindables.BindingActivity
import com.skydoves.bundler.intentOf
import com.skydoves.pokedexar.R
import com.skydoves.pokedexar.database.BoxData
import com.skydoves.pokedexar.database.BoxListService
import com.skydoves.pokedexar.databinding.ActivitySceneBinding
import com.skydoves.pokedexar.extensions.applyFullScreenWindow
import com.skydoves.pokedexar.ui.login.Login
import com.skydoves.pokedexar.ui.login.LoginService
import com.skydoves.pokedexar.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

@AndroidEntryPoint
class HomeActivity : BindingActivity<ActivitySceneBinding>(R.layout.activity_home) {

  private val viewModel by viewModels<HomeViewModel>()

  lateinit var dialog01: Dialog

  // This property is only valid between onCreateView and
  // onDestroyView.

  private var boxpokemonList = arrayListOf<Pokemon>(
    Pokemon("aa", "피카츄", "pokemon01"),
    Pokemon("aa", "파이리", "pokemon02"),
    Pokemon("aa", "꼬부기", "pokemon03"),
    Pokemon("aa", "이상해씨", "pokemon04"),
    Pokemon("aa", "잉어킹", "pokemon05"),
    Pokemon("aa", "잠만보", "pokemon06"),
    Pokemon("aa", "구구", "pokemon07"),
    Pokemon("aa", "뮤", "pokemon00"),
  )

  lateinit var pokeboxAdapter : GVAdapter2

  var selected_pokemonList = arrayListOf<Pokemon>(
    Pokemon("aa", "피카츄", "pokemon01"),
    Pokemon("aa", "파이리", "pokemon02"),
    Pokemon("aa", "꼬부기", "pokemon03"),
    Pokemon("aa", "이상해씨", "pokemon04")
  )

  override fun onCreate(savedInstanceState: Bundle?) {
    applyFullScreenWindow()
    super.onCreate(savedInstanceState)

    pokeboxAdapter = GVAdapter2(this)

    val retrofit = Retrofit.Builder().baseUrl("http://192.249.18.193:80")
      .addConverterFactory(GsonConverterFactory.create()).build()
    val service = retrofit.create(BoxListService::class.java)
    service.requestBoxList( "Token ${EasySharedPreference.Companion.getString("token", "noToken")}" ).enqueue(
      object : Callback<Array<BoxData>> {
        override fun onFailure(call: Call<Array<BoxData>>, t: Throwable) {

        }

        override fun onResponse(call: Call<Array<BoxData>>, response: Response<Array<BoxData>>) {
          println("Hello I am Homeactivity~~")
          println(response.body())
          val arr:Array<BoxData> = response.body()!!
          pokeboxAdapter.boxList = arr
          pokeboxAdapter.notifyDataSetChanged()
        }
      }
    )

    //pokeboxAdapter = GVAdapter(this, boxpokemonList)

    val gridView = findViewById<GridView>(R.id.gridView)
    gridView.adapter = pokeboxAdapter

    showSelectedPokemon(selected_pokemonList)

  }

  fun showSelectedPokemon(selected_pokemon:List<Pokemon>){
    dialog01 = Dialog(this)
    dialog01.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog01.setContentView(R.layout.pokemon_select_dialog)

    val pokemon1 = selected_pokemonList[0]
    val pokemon1_name = findViewById<TextView>(R.id.pokemon1_name)
    val pokemon1_img = findViewById<ImageView>(R.id.pokemon1_img)
    val resourceId1 = this.resources.getIdentifier(pokemon1.image, "drawable", this.packageName)
    pokemon1_img.setImageResource(resourceId1)
    pokemon1_name.text = pokemon1.name
    pokemon1_img.setOnLongClickListener {
      showDialog01(0)
      true
    }

    val pokemon2 = selected_pokemonList[1]
    val pokemon2_name = findViewById<TextView>(R.id.pokemon2_name)
    val pokemon2_img = findViewById<ImageView>(R.id.pokemon2_img)
    val resourceId2 = this.resources.getIdentifier(pokemon2.image, "drawable", this.packageName)
    pokemon2_img.setImageResource(resourceId2)
    pokemon2_name.text = pokemon2.name
    pokemon2_img.setOnLongClickListener {
      showDialog01(1)
      true
    }

    val pokemon3 = selected_pokemonList[2]
    val pokemon3_name = findViewById<TextView>(R.id.pokemon3_name)
    val pokemon3_img = findViewById<ImageView>(R.id.pokemon3_img)
    val resourceId3 = this.resources.getIdentifier(pokemon3.image, "drawable", this.packageName)
    pokemon3_img.setImageResource(resourceId3)
    pokemon3_name.text = pokemon3.name
    pokemon3_img.setOnLongClickListener {
      showDialog01(2)
      true
    }

    val pokemon4 = selected_pokemonList[3]
    val pokemon4_name = findViewById<TextView>(R.id.pokemon4_name)
    val pokemon4_img = findViewById<ImageView>(R.id.pokemon4_img)
    val resourceId4 = this.resources.getIdentifier(pokemon4.image, "drawable", this.packageName)
    pokemon4_img.setImageResource(resourceId4)
    pokemon4_name.text = pokemon4.name
    pokemon4_img.setOnLongClickListener {
      showDialog01(3)
      true
    }
  }

  fun showDialog01(selceted_idx :Int){
    dialog01.show()

    val cancel_btn = dialog01.findViewById<Button>(R.id.cancel_btn)
    cancel_btn.setOnClickListener{
      dialog01.dismiss()
    }

    val delete_btn = dialog01.findViewById<Button>(R.id.delete_btn)
    delete_btn.setOnClickListener{
      selected_pokemonList[selceted_idx] = Pokemon("aa","sumin", "nopokemon")
      dialog01.dismiss()
      showSelectedPokemon(selected_pokemonList)
    }

    val pokemonbox = dialog01.findViewById<GridView>(R.id.gridView)
    val pokeboxAdapter = GVAdapter(this, boxpokemonList)
    pokeboxAdapter.setOnItemClickListener( object : GVAdapter.OnItemClickListener {
      override fun onItemClick(v: View?, pos: Int) {
        selected_pokemonList[selceted_idx] = boxpokemonList[pos]
        dialog01.dismiss()
        showSelectedPokemon(selected_pokemonList)
      }
    })

    pokemonbox.adapter = pokeboxAdapter



  }

  companion object {
    fun startActivity(context: Context) {
      context.intentOf<HomeActivity> {
        startActivity(context)
      }
    }
  }
}
