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

package com.skydoves.pokedexar.ui.home

import androidx.annotation.MainThread
import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.BindingViewModel
import com.skydoves.bindables.asBindingProperty
import com.skydoves.bindables.bindingProperty
import com.skydoves.pokedexar.model.Pokemon
import com.skydoves.pokedexar.repository.SceneRepository
import com.skydoves.pokedexar_core.RenderingModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val sceneRepository: SceneRepository
) : BindingViewModel() {

  private val pokemonFetchingModel: MutableStateFlow<RenderingModel?> = MutableStateFlow(null)

  private val pokemonListFlow = pokemonFetchingModel.filterNotNull().flatMapLatest { model ->
    sceneRepository.insertPokemon(
      pokemonModel = model,
      onError = { errorMessage = it }
    )
  }

  @get:Bindable
  val pokemon: Pokemon? by pokemonListFlow.asBindingProperty(viewModelScope, null)

  @get:Bindable
  var errorMessage: String? by bindingProperty(null)
    private set

  var isCaught: Boolean = false
    private set

  init {
    Timber.d("init SceneViewModel")
  }

  @MainThread
  fun insertPokemonModel(renderingModel: RenderingModel) =
    pokemonFetchingModel.tryEmit(renderingModel)
}
