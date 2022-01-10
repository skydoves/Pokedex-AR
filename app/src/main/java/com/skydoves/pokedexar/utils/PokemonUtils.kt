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

package com.skydoves.pokedexar.utils

import com.skydoves.pokedexar.R

object PokemonUtils {

  fun getTypeColor(type: String): Int {
    return when (type) {
      "격투" -> R.color.fighting
      "비행" -> R.color.flying
      "독" -> R.color.poison
      "땅" -> R.color.ground
      "바위" -> R.color.rock
      "벌레" -> R.color.bug
      "고스트" -> R.color.ghost
      "강철" -> R.color.steel
      "불" -> R.color.fire
      "물" -> R.color.water
      "풀" -> R.color.grass
      "전기" -> R.color.electric
      "에스퍼" -> R.color.psychic
      "얼음" -> R.color.ice
      "드래곤" -> R.color.dragon
      "페어리" -> R.color.fairy
      "악" -> R.color.dark
      else -> R.color.gray_21
    }
  }

  fun getPokemonId(name: String): Int {
    return when (name) {
      "eevee" -> 133
      "abra" -> 63
      "bulbasaur" -> 1
      "charmander" -> 4
      "dratini" -> 147
      "jigglypuff" -> 39
      "magikarp" -> 129
      "mew" -> 151
      "oddish" -> 43
      "pikachu" -> 25
      "poliwag" -> 60
      "snorlax" -> 143
      "squirtle" -> 7
      else -> 0
    }
  }
}
