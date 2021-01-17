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

package com.skydoves.pokedexar.di

import com.skydoves.pokedexar.network.PokedexClient
import com.skydoves.pokedexar.persistence.PokemonDao
import com.skydoves.pokedexar.persistence.PokemonInfoDao
import com.skydoves.pokedexar.repository.DetailRepository
import com.skydoves.pokedexar.repository.MainRepository
import com.skydoves.pokedexar.repository.SceneRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

  @Provides
  @ViewModelScoped
  fun provideMainRepository(
    pokemonDao: PokemonDao
  ): MainRepository {
    return MainRepository(pokemonDao)
  }

  @Provides
  @ViewModelScoped
  fun provideSceneRepository(
    pokemonDao: PokemonDao
  ): SceneRepository {
    return SceneRepository(pokemonDao)
  }

  @Provides
  @ViewModelScoped
  fun provideDetailRepository(
    pokedexClient: PokedexClient,
    pokemonInfoDao: PokemonInfoDao
  ): DetailRepository {
    return DetailRepository(pokedexClient, pokemonInfoDao)
  }
}
