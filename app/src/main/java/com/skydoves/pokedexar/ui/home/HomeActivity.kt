package com.skydoves.pokedexar.ui.home

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.*
import androidx.activity.viewModels
import com.amn.easysharedpreferences.EasySharedPreference
import com.skydoves.bindables.BindingActivity
import com.skydoves.bundler.intentOf
import com.skydoves.pokedexar.R
import com.skydoves.pokedexar.database.BoxData
import com.skydoves.pokedexar.database.BoxListService
import com.skydoves.pokedexar.database.DataIO
import com.skydoves.pokedexar.databinding.ActivitySceneBinding
import com.skydoves.pokedexar.extensions.applyFullScreenWindow
import com.skydoves.pokedexar.ui.login.Login
import com.skydoves.pokedexar.ui.login.LoginService
import com.skydoves.pokedexar.ui.main.MainActivity
import com.skydoves.pokedexar.utils.PokemonUtils
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

  var pokemonutils = PokemonUtils

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
          pokeboxAdapter.setOnLongItemClickListener(
            object : GVAdapter2.OnLongItemClickListener{
              override fun onLongItemClick(v: View?, pos: Int) {
                val box = pokeboxAdapter.boxList[pos]
                val dialog = Dialog(this@HomeActivity)
                dialog.setContentView(R.layout.dialog_detail_example)

                val resourceId = resources.getIdentifier("pokemon${box.pokemon.id}", "drawable", packageName)
                dialog.findViewById<ImageView>(R.id.detail_img).setImageResource(resourceId)
                dialog.findViewById<TextView>(R.id.detail_name).text = box.pokemon.name

                dialog.findViewById<TextView>(R.id.detail_type1).text = box.pokemon.type1.name
                dialog.findViewById<TextView>(R.id.detail_type1).setBackgroundColor(getTypeColor(box.pokemon.type1.name))
                Log.d("t1","${getTypeColor(box.pokemon.type1.name)}")
                if (box.pokemon.type2.name == "None") {
                  dialog.findViewById<TextView>(R.id.detail_type2).setVisibility(View.GONE)
                }
                dialog.findViewById<TextView>(R.id.detail_type2).text = box.pokemon.type2.name
                dialog.findViewById<TextView>(R.id.detail_type2).setBackgroundColor(getTypeColor(box.pokemon.type2.name))

                dialog.findViewById<TextView>(R.id.detail_atk).text = box.pokemon.atk.toString()
                dialog.findViewById<TextView>(R.id.detail_def).text = box.pokemon.dfs.toString()
                dialog.findViewById<TextView>(R.id.detail_stk).text = box.pokemon.stk.toString()
                dialog.findViewById<TextView>(R.id.detail_sef).text = box.pokemon.sef.toString()
                dialog.findViewById<TextView>(R.id.detail_spd).text = box.pokemon.spd.toString()
                dialog.findViewById<TextView>(R.id.detail_hp).text = box.pokemon.hp.toString()

                dialog.findViewById<ProgressBar>(R.id.progress_hp).progress = box.pokemon.hp
                dialog.findViewById<ProgressBar>(R.id.progress_atk).progress = box.pokemon.atk
                dialog.findViewById<ProgressBar>(R.id.progress_def).progress = box.pokemon.dfs
                dialog.findViewById<ProgressBar>(R.id.progress_stk).progress = box.pokemon.stk
                dialog.findViewById<ProgressBar>(R.id.progress_sef).progress = box.pokemon.sef
                dialog.findViewById<ProgressBar>(R.id.progress_spd).progress = box.pokemon.spd

                dialog.findViewById<TextView>(R.id.detail_skill1).text = box.skill1.name
                dialog.findViewById<TextView>(R.id.detail_skill2).text = box.skill2.name
                dialog.findViewById<TextView>(R.id.detail_skill3).text = box.skill3.name
                dialog.findViewById<TextView>(R.id.detail_skill4).text = box.skill4.name

                dialog.findViewById<TextView>(R.id.detail_skill1).setBackgroundColor(getTypeColor(box.skill1.type.name))
                dialog.findViewById<TextView>(R.id.detail_skill2).setBackgroundColor(getTypeColor(box.skill2.type.name))
                dialog.findViewById<TextView>(R.id.detail_skill3).setBackgroundColor(getTypeColor(box.skill3.type.name))
                dialog.findViewById<TextView>(R.id.detail_skill4).setBackgroundColor(getTypeColor(box.skill4.type.name))

                dialog.findViewById<Button>(R.id.release_button).setOnClickListener {
                  DataIO.deleteBoxAndDo(box.id){
                    finish() //인텐트 종료
                    overridePendingTransition(0, 0) //인텐트 효과 없애기
                    val intent = intent //인텐트
                    startActivity(intent) //액티비티 열기
                    overridePendingTransition(0, 0) //인텐트 효과 없애기
                    dialog.dismiss()
                  }
                }

                dialog.findViewById<Button>(R.id.close_button).setOnClickListener {
                  dialog.dismiss()
                }

                dialog.show()
              }
            }
          )
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


  fun getTypeColor(type: String): Int {
    return when (type) {
      "격투" -> R.color.fighting
      "비행" -> R.color.flying
      "독" -> 2137270149
      "땅" -> R.color.ground
      "바위" -> R.color.rock
      "벌레" -> R.color.bug
      "고스트" -> R.color.ghost
      "강철" -> R.color.steel
      "불" -> 2142380840
      "물" -> 2133215452
      "풀" -> 2130738242
      "전기" -> 2145445451
      "에스퍼" -> 2141989227
      "얼음" -> R.color.ice
      "드래곤" -> 2134346388
      "페어리" -> 2141067844
      "악" -> R.color.dark
      else -> 2142348709
    }
  }

  companion object {
    fun startActivity(context: Context) {
      context.intentOf<HomeActivity> {
        startActivity(context)
      }
    }
  }
}
