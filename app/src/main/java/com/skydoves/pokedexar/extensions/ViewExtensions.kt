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

package com.skydoves.pokedexar.extensions

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.BounceInterpolator

fun View.doElasticAnimation() {
  scaleX = 0f
  scaleY = 0f
  visibility = View.VISIBLE

  ValueAnimator.ofFloat(0f, 1f).apply {
    duration = 250L
    interpolator = BounceInterpolator()
    addUpdateListener {
      val value = it.animatedValue as Float
      scaleX = value
      scaleY = value
    }
    start()
  }
}
