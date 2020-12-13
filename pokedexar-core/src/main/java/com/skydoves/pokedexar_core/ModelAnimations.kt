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

import android.animation.ObjectAnimator
import android.view.animation.AccelerateDecelerateInterpolator
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.QuaternionEvaluator
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.math.Vector3Evaluator

/** ModelAnimations is a helper class for executing animations to a target [Node]. */
object ModelAnimations {

  /**
   * Translates a target node to a desired position.
   *
   * @param anchorNode A target [Node] for translating.
   * @param targetPosition A target position for translating the [Node].
   * @param durationTime A duration time for executing this animation.
   * @param doWhenFinish A lambda for executing after finish this animation.
   */
  inline fun translateModel(
    anchorNode: Node,
    targetPosition: Vector3,
    durationTime: Long = 150L,
    crossinline doWhenFinish: () -> Unit = {}
  ) {
    ObjectAnimator().apply {
      setAutoCancel(false)
      target = anchorNode
      duration = durationTime
      setObjectValues(
        anchorNode.localPosition,
        targetPosition
      )
      setPropertyName("localPosition")
      setEvaluator(Vector3Evaluator())
      interpolator = AccelerateDecelerateInterpolator()
      start()
    }.doWhenFinish { doWhenFinish() }
  }

  /**
   * Rotates a target node to a desired position.
   *
   * @param anchorNode A target [Node] for translating.
   * @param durationTime A duration time for executing this animation.
   * @param doWhenFinish A lambda for executing after finish this animation.
   */
  inline fun rotateModel(
    anchorNode: Node,
    durationTime: Long = 150L,
    crossinline doWhenFinish: () -> Unit = {}
  ) {
    ObjectAnimator().apply {
      setAutoCancel(false)
      target = anchorNode
      duration = durationTime
      setObjectValues(
        Quaternion.axisAngle(Vector3(0.0f, 0.0f, 0.0f), 0.0f),
        Quaternion.axisAngle(Vector3(2.0f, 2.0f, 2.0f), 2360f)
      )
      setPropertyName("localRotation")
      setEvaluator(QuaternionEvaluator())
      interpolator = AccelerateDecelerateInterpolator()
      start()
    }.doWhenFinish { doWhenFinish() }
  }
}
