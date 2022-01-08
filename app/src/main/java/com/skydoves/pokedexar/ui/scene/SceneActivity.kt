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
import androidx.activity.viewModels
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
import com.skydoves.pokedexar_core.ModelRenderer
import com.skydoves.pokedexar_core.PokemonModels
import com.skydoves.whatif.whatIfNotNull
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SceneActivity : BindingActivity<ActivitySceneBinding>(R.layout.activity_scene) {

  private val viewModel by viewModels<HomeViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    applyFullScreenWindow()
    super.onCreate(savedInstanceState)

    binding.lifecycleOwner = this
    binding.vm = viewModel

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

  companion object {
    fun startActivity(context: Context) {
      context.intentOf<SceneActivity> {
        startActivity(context)
      }
    }
  }
}
