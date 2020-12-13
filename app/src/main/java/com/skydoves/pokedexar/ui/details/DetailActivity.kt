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

package com.skydoves.pokedexar.ui.details

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import com.google.ar.core.Pose
import com.google.ar.core.Session
import com.google.ar.core.TrackingState
import com.google.ar.sceneform.ux.ArFragment
import com.skydoves.bundler.bundleNonNull
import com.skydoves.bundler.intentOf
import com.skydoves.pokedexar.R
import com.skydoves.pokedexar.base.DataBindingActivity
import com.skydoves.pokedexar.databinding.ActivityDetailBinding
import com.skydoves.pokedexar.extensions.findFragmentAs
import com.skydoves.pokedexar.model.Pokemon
import com.skydoves.pokedexar_core.ModelRenderer
import com.skydoves.pokedexar_core.PokemonModels
import com.skydoves.pokedexar_core.PokemonModels.DEFAULT_POSITION_DETAILS_POKEMON
import com.skydoves.whatif.whatIfNotNull
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : DataBindingActivity() {

  private val viewModel: DetailViewModel by viewModels()
  private val binding: ActivityDetailBinding by binding(R.layout.activity_detail)
  private val pokemon: Pokemon by bundleNonNull("EXTRA_POKEMON")

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    with(binding) {
      lifecycleOwner = this@DetailActivity
      pokemon = this@DetailActivity.pokemon
      vm = viewModel.apply {
        fetchPokemonInfo(this@DetailActivity.pokemon.name)
      }
    }

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
    if (session.allAnchors.isEmpty()) {
      val pose = Pose(floatArrayOf(0f, 0f, -1f), floatArrayOf(0f, 0f, 0f, 1f))
      session.createAnchor(pose).apply {
        val garden = PokemonModels.getGarden()
        ModelRenderer.renderObject(this@DetailActivity, garden) { renderable ->
          ModelRenderer.addGardenOnScene(arFragment, this, renderable, garden)
        }

        val pokemon = PokemonModels.getPokemonByName(this@DetailActivity.pokemon.name)
          .copy(localPosition = DEFAULT_POSITION_DETAILS_POKEMON)
        ModelRenderer.renderObject(this@DetailActivity, pokemon) { renderable ->
          ModelRenderer.addPokemonOnScene(arFragment, this, renderable, pokemon)
        }
      }
    }
  }

  companion object {
    @VisibleForTesting
    const val EXTRA_POKEMON = "EXTRA_POKEMON"

    fun startActivity(view: View, pokemon: Pokemon) {
      view.context.intentOf<DetailActivity> {
        putExtra(EXTRA_POKEMON, pokemon)
        startActivity(view.context)
      }
    }
  }
}
