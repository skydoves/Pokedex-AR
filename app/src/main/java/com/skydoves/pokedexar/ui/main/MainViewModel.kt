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

package com.skydoves.pokedexar.ui.main

import androidx.databinding.Bindable
import androidx.lifecycle.viewModelScope
import com.skydoves.bindables.asBindingProperty
import com.skydoves.bindables.bindingProperty
import com.skydoves.pokedexar.base.LiveCoroutinesViewModel
import com.skydoves.pokedexar.model.Pokemon
import com.skydoves.pokedexar.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  mainRepository: MainRepository
) : LiveCoroutinesViewModel() {

  @get:Bindable
  var errorMessage: String? by bindingProperty(null)
    private set

  @get:Bindable
  var isLoading: Boolean by bindingProperty(true)
    private set

  private val pokemonListFlow = mainRepository.getPokemonList(
    onStart = { isLoading = false },
    onError = { errorMessage = it }
  )

  @get:Bindable
  val pokemonList: List<Pokemon>? by pokemonListFlow.asBindingProperty(viewModelScope, null)

  init {
    Timber.d("init MainViewModel")
  }
}
