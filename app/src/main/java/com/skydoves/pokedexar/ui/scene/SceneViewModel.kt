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

import androidx.annotation.MainThread
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import com.skydoves.bindables.bindingProperty
import com.skydoves.pokedexar.base.LiveCoroutinesViewModel
import com.skydoves.pokedexar.model.Pokemon
import com.skydoves.pokedexar.repository.SceneRepository
import com.skydoves.pokedexar_core.RenderingModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SceneViewModel @Inject constructor(
  private val sceneRepository: SceneRepository
) : LiveCoroutinesViewModel() {

  private val pokemonFetchingModel: MutableLiveData<RenderingModel> = MutableLiveData()
  val pokemonListLiveData: LiveData<Pokemon>

  @get:Bindable
  var errorMessage: String? by bindingProperty(null)
    private set

  var isCaught: Boolean = false
    private set

  init {
    Timber.d("init SceneViewModel")

    pokemonListLiveData = pokemonFetchingModel.switchMap {
      isCaught = true
      launchOnViewModelScope {
        this.sceneRepository.insertPokemon(
          pokemonModel = it,
          onError = { errorMessage = it }
        ).asLiveData()
      }
    }
  }

  @MainThread
  fun insertPokemonModel(renderingModel: RenderingModel) {
    pokemonFetchingModel.value = renderingModel
  }
}
