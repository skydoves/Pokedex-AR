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

/**
 * A rendering model that can be drawn on the AR frame.
 *
 * @param name A name of the model.
 * @param model A rendering source.
 * @param direction A default direction of the model when initializing.
 * @param scale A default scale of the model when initializing.
 * @param localPosition A default local of the model when initializing.
 */
data class RenderingModel(
  val name: String,
  val model: String,
  val direction: Vector3 = Vector3(0f, 0f, 1f),
  val scale: Float = 1f,
  val localPosition: Vector3 = Vector3(0.5f, 0.5f, 0.5f)
)
