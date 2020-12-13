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

import android.animation.Animator
import android.animation.ObjectAnimator

/**
 * An [ObjectAnimator] extension for executing a lambda after finish the animation.
 *
 * @param block A lambda for executing after finish the animation.
 */
inline fun ObjectAnimator.doWhenFinish(
  crossinline block: () -> Unit
) {
  addListener(object : Animator.AnimatorListener {
    override fun onAnimationEnd(animation: Animator?) = block()
    override fun onAnimationStart(animation: Animator?) = Unit
    override fun onAnimationCancel(animation: Animator?) = Unit
    override fun onAnimationRepeat(animation: Animator?) = Unit
  })
}
