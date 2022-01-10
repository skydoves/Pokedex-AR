/*
 * Designed and developed by 2020 skydoves (Jaewoong Eum)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.skydoves.pokedexar.ui.scene

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.ViewDataBinding
import com.google.ar.core.Pose
import com.google.ar.core.Session
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.ux.ArFragment
import com.skydoves.bindables.BindingActivity
import com.skydoves.bundler.intentOf
import com.skydoves.pokedexar.R
import com.skydoves.pokedexar.databinding.ActivitySceneBinding
import com.skydoves.pokedexar.extensions.applyFullScreenWindow
import com.skydoves.pokedexar.extensions.findFragmentAs
import com.skydoves.pokedexar.ui.home.HomeViewModel
import com.skydoves.pokedexar.ui.room.SocketHandler
import com.skydoves.pokedexar_core.ModelRenderer
import com.skydoves.pokedexar_core.PokemonModels
import com.skydoves.whatif.whatIfNotNull
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject
import com.amn.easysharedpreferences.EasySharedPreference
import org.json.JSONArray
import kotlin.math.ceil

@AndroidEntryPoint
class SceneActivity : BindingActivity<ActivitySceneBinding>(R.layout.activity_scene) {

  private val viewModel by viewModels<HomeViewModel>()
  private lateinit var mSocket : Socket

  override fun onCreate(savedInstanceState: Bundle?) {
    applyFullScreenWindow()
    super.onCreate(savedInstanceState)
    binding.lifecycleOwner = this
    binding.vm = viewModel

    roomId = EasySharedPreference.Companion.getString("roomId", "Default")
    myId = EasySharedPreference.Companion.getString("myId", "Default")
    val startObj = JSONObject(EasySharedPreference.Companion.getString("startObject", "Default"))

    updatePokemon(startObj.getJSONArray("fights"))

    with(findFragmentAs<ArFragment>(R.id.arFragment)) {
      planeDiscoveryController.hide()
      planeDiscoveryController.setInstructionView(null)
      arSceneView.planeRenderer.isVisible = false
      arSceneView.scene.addOnUpdateListener {
        onUpdate(it)

        // checks the state of the AR frame is Tracking.
        val arFrame = arSceneView.arFrame ?: return@addOnUpdateListener
        if (arFrame.camera?.trackingState != TrackingState.TRACKING) {
          return@addOnUpdateListener
        }

        // initialize the global anchor with default rendering models.
        arSceneView.session.whatIfNotNull { session ->
          initializeModels(this, session)
        }
      }

      // init button interactions, hp, etc
      initializeUI()
    }
  }

  private fun initializeModels(arFragment: ArFragment, session: Session) {
    if (session.allAnchors.isEmpty() && !viewModel.isCaught) {
      val pose = Pose(floatArrayOf(0f, 0f, -1f), floatArrayOf(0f, 0f, 0f, 1f))
      session.createAnchor(pose).apply {
        val pokemon1 = PokemonModels.getRandomPokemon().copy(localPosition = PokemonModels.DEFAULT_POSITION_DETAILS_POKEMON1)
        ModelRenderer.renderObject(this@SceneActivity, pokemon1) { renderable ->
          ModelRenderer.addPokemonOnScene(arFragment, this, renderable, pokemon1)
        }

        val pokemon2 = PokemonModels.getRandomPokemon().copy(localPosition = PokemonModels.DEFAULT_POSITION_DETAILS_POKEMON2)
        ModelRenderer.renderObject(this@SceneActivity, pokemon2) { renderable ->
          ModelRenderer.addPokemonOnScene(arFragment, this, renderable, pokemon2)
        }
      }
    }
  }

  // Update UI after receive response
  var onBattleResult = Emitter.Listener { args ->
    val obj = JSONObject(args[0].toString())

    val state = obj.getJSONObject("state")
    val stateKey = state.getString("key")

    if (stateKey == "default" || stateKey == "switch") {
      Thread(object : Runnable{
        override fun run() {
          runOnUiThread(Runnable {
            val fightsObj = obj.getJSONArray("fights")

            kotlin.run {
              if (stateKey == "switch") {
                // 죽은 포켓몬과 다음 포켓몬 교체
                for (i in 0 until fightsObj.length()) {
                  var fo = fightsObj.getJSONObject(i)
                  if (fo.getString("result") == "die") {
                    fightsObj.put(i, state.getJSONObject("switch"))
                  }
                }
              }

              updatePokemon(fightsObj)

              // set Timer
//            obj.getInt("timer")
            }
          })
        }
      }).start()
    } else if (stateKey == "end") {
      // End Battle
    } else {
      // ...
    }
  }

  private fun initializeUI() {
    mSocket = SocketHandler.getSocket()
    mSocket.on("battle_result", onBattleResult)

    binding.battleBtnSkill1.setOnClickListener {
      val obj = JSONObject()
      obj.put("roomId", roomId)
      obj.put("skillIndex", 0)
      mSocket.emit("skill", obj)
    }
    binding.battleBtnSkill2.setOnClickListener {
      val obj = JSONObject()
      obj.put("roomId", roomId)
      obj.put("skillIndex", 1)
      mSocket.emit("skill", obj)
    }
    binding.battleBtnSkill3.setOnClickListener {
      val obj = JSONObject()
      obj.put("roomId", roomId)
      obj.put("skillIndex", 2)
      mSocket.emit("skill", obj)
    }
    binding.battleBtnSkill4.setOnClickListener {
      val obj = JSONObject()
      obj.put("roomId", roomId)
      obj.put("skillIndex", 3)
      mSocket.emit("skill", obj)
    }
  }

  companion object {
    fun startActivity(context: Context) {
      context.intentOf<SceneActivity> {
        startActivity(context)
      }
    }
  }

  lateinit var myId: String
  lateinit var roomId: String
  var myFighterHp: Double = 0.0
  var opFighterHp: Double = 0.0



  /*[{ownerId: p.id,
  id: p.fighter.id,
  hp: p.fighter.hp,
  name: p.fighter.name}, ...]*/
  fun updatePokemon(resultObj : JSONArray) {
    val fight1 = resultObj[0] as JSONObject
    val fight2 = resultObj[1] as JSONObject
    val id1 = fight1.get("ownerId")
    val id2 = fight2.get("ownerId")

    var myFighterName: String = ""
    var opFighterName: String = ""

    // 내 포켓몬 찾기
    if (id1 == myId) {
      myFighterName = fight1.getString("name")
      opFighterName = fight2.getString("name")
      myFighterHp = fight1.getDouble("hp")
      opFighterHp = fight2.getDouble("hp")

      var toastText = fight1.getString("effect")
      if (toastText != "") {
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
      }
    } else {
      myFighterName = fight2.getString("name")
      opFighterName = fight1.getString("name")
      myFighterHp = fight2.getDouble("hp")
      opFighterHp = fight1.getDouble("hp")

      var toastText = fight2.getString("effect")
      if (toastText != "") {
        Toast.makeText(this, toastText, Toast.LENGTH_SHORT).show()
      }
    }

    binding.battleTextNameMe.setText(myFighterName)
    binding.battleTextNameOp.setText(opFighterName)
    binding.battleTextHpMe.setText(ceil(myFighterHp).toInt().toString())
    binding.battleTextHpOp.setText(ceil(opFighterHp).toInt().toString())
  }
}
