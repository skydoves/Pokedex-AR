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

package com.skydoves.pokedexar_core

import com.google.ar.sceneform.math.Vector3
import kotlin.random.Random

object PokemonModels {

  private fun getEevee() = RenderingModel(
    name = "eevee",
    model = "eevee.sfb",
    localPosition = DEFAULT_POSITION_POKEMON
  )

  private fun getAbra() = RenderingModel(
    name = "abra",
    model = "abra.sfb",
    scale = 2.0f,
    localPosition = DEFAULT_POSITION_POKEMON
  )

  private fun getBulbasaur() = RenderingModel(
    name = "bulbasaur",
    model = "bulbasaur.sfb",
    localPosition = DEFAULT_POSITION_POKEMON
  )

  private fun getCharmander() = RenderingModel(
    name = "charmander",
    model = "charmander.sfb",
    localPosition = DEFAULT_POSITION_POKEMON
  )

  private fun getDratini() = RenderingModel(
    name = "dratini",
    model = "dratini.sfb",
    scale = 0.7f,
    localPosition = DEFAULT_POSITION_POKEMON
  )

  private fun getJigglypuff() = RenderingModel(
    name = "jigglypuff",
    model = "jigglypuff.sfb",
    scale = 0.45f,
    localPosition = DEFAULT_POSITION_POKEMON
  )

  fun getMagikarp() = RenderingModel(
    name = "magikarp",
    model = "magikarp.sfb",
    direction = Vector3(0.5f, 0f, 1f),
    localPosition = DEFAULT_POSITION_POKEMON
  )

  private fun getMew() = RenderingModel(
    name = "mew",
    model = "mew.sfb",
    scale = 1.5f,
    localPosition = DEFAULT_POSITION_POKEMON,
  )

  private fun getOddish() = RenderingModel(
    name = "oddish",
    model = "oddish.sfb",
    localPosition = DEFAULT_POSITION_POKEMON
  )

  private fun getPikachu() = RenderingModel(
    name = "pikachu",
    model = "pikachu.sfb",
    scale = 0.85f,
    localPosition = DEFAULT_POSITION_POKEMON
  )

  private fun getPoliwag() = RenderingModel(
    name = "poliwag",
    model = "poliwag.sfb",
    localPosition = DEFAULT_POSITION_POKEMON
  )

  private fun getSnorlax() = RenderingModel(
    name = "snorlax",
    model = "snorlax.sfb",
    scale = 3f,
    localPosition = DEFAULT_POSITION_POKEMON
  )

  private fun getSquirtle() = RenderingModel(
    name = "squirtle",
    model = "squirtle.sfb",
    direction = Vector3(0.75f, 0f, 1f),
    scale = 2.5f,
    localPosition = DEFAULT_POSITION_POKEMON
  )

  fun getPokeball() = RenderingModel(
    name = "pokeball",
    model = "pokeball.sfb",
    scale = 0.1f,
    localPosition = DEFAULT_POSITION_POKE_BALL
  )

  fun getGarden() = RenderingModel(
    name = "garden",
    model = "garden.sfb",
    localPosition = DEFAULT_POSITION_GARDEN
  )

  fun getRandomPokemon(): RenderingModel {
    return when (Random.nextInt(13)) {
      0 -> getAbra()
      1 -> getBulbasaur()
      2 -> getCharmander()
      3 -> getDratini()
      4 -> getJigglypuff()
      5 -> getMagikarp()
      6 -> getMew()
      7 -> getOddish()
      8 -> getPikachu()
      9 -> getPoliwag()
      10 -> getSnorlax()
      11 -> getSquirtle()
      else -> getEevee()
    }
  }

  fun getPokemonByName(name: String): RenderingModel {
    return when (name) {
      "abra" -> getAbra()
      "bulbasaur" -> getBulbasaur()
      "charmander" -> getCharmander()
      "dratini" -> getDratini()
      "jigglypuff" -> getJigglypuff()
      "magikarp" -> getMagikarp()
      "mew" -> getMew()
      "oddish" -> getOddish()
      "pikachu" -> getPikachu()
      "poliwag" -> getPoliwag()
      "snorlax" -> getSnorlax()
      "squirtle" -> getSquirtle()
      else -> getEevee()
    }
  }

  private val DEFAULT_POSITION_POKEMON = Vector3(0f, -0.25f, -3f)

  private val DEFAULT_POSITION_GARDEN = Vector3(0f, -1f, -3f)

  val DEFAULT_POSITION_DETAILS_POKEMON = Vector3(0f, -0.88f, -2f)

  val DEFAULT_POSITION_POKE_BALL = Vector3(0f, -0.5f, 0.5f)
}
