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

package com.skydoves.pokedexar.repository

import androidx.annotation.WorkerThread
import com.skydoves.pokedexar.model.Pokemon
import com.skydoves.pokedexar.persistence.PokemonDao
import com.skydoves.pokedexar.utils.PokemonUtils
import com.skydoves.pokedexar_core.RenderingModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SceneRepository @Inject constructor(
  private val pokemonDao: PokemonDao
) : Repository {

  @WorkerThread
  suspend fun insertPokemon(
    pokemonModel: RenderingModel,
    onError: (String?) -> Unit
  ) = flow {
    val id = PokemonUtils.getPokemonId(pokemonModel.name)
    val previousPokemon = pokemonDao.getPokemon(id)

    val pokemon = previousPokemon?.copy(
      hits = previousPokemon.hits + 1
    ) ?: Pokemon(
      id = PokemonUtils.getPokemonId(pokemonModel.name),
      name = pokemonModel.name,
      hits = 1
    )

    pokemonDao.insertPokemon(pokemon)
    emit(pokemon)
  }.catch {
    onError(it.localizedMessage)
  }.flowOn(Dispatchers.IO)
}
