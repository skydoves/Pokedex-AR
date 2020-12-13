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

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.view.MotionEvent
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import com.skydoves.pokedexar_core.PokemonModels.DEFAULT_POSITION_POKE_BALL
import kotlin.random.Random

object ModelRenderer {

  /**
   * Render a model using the [ModelRenderable].
   *
   * @param context Context for getting model source.
   * @param renderingModel A [RenderingModel] which has a rendering information.
   * @param thenAccept A lambda for executing if ready to use the rendered model.
   */
  inline fun renderObject(
    context: Context,
    renderingModel: RenderingModel,
    crossinline thenAccept: (Renderable) -> Unit
  ) {
    ModelRenderable.builder()
      .setSource(context, Uri.parse(renderingModel.model))
      .build()
      .thenAccept { thenAccept(it) }
      .exceptionally {
        AlertDialog.Builder(context)
          .setMessage(it.localizedMessage)
          .show()
        return@exceptionally null
      }
  }

  /**
   * Adds a pokemon object to the AR scene.
   *
   * @param fragment The target [ArFragment] for adding a new pokemon node.
   * @param anchor An anchor for creating a new [AnchorNode].
   * @param renderable A [Renderable] for setting it to display for the [AnchorNode].
   * @param renderingModel A model information for displaying on the scene.
   */
  fun addPokemonOnScene(
    fragment: ArFragment,
    anchor: Anchor,
    renderable: Renderable,
    renderingModel: RenderingModel
  ) {
    val anchorNode = AnchorNode(anchor)
    TransformableNode(fragment.transformationSystem).apply {
      name = renderingModel.name
      localPosition = renderingModel.localPosition
      this.renderable = renderable
      translationController.isEnabled = false
      setParent(anchorNode)
      setLookDirection(renderingModel.direction)
      scaleController.minScale = renderingModel.scale
      scaleController.maxScale = renderingModel.scale + 0.05f
      fragment.arSceneView.scene.addChild(anchorNode)
      setOnTouchListener { hitTestResult, motionEvent ->
        if (motionEvent.action == MotionEvent.ACTION_UP) {
          hitTestResult.node?.let { node ->
            node.setLookDirection(Vector3(0f, 0f, 1f))
            ModelAnimations.translateModel(
              anchorNode = node,
              targetPosition = Vector3(
                localPosition.x,
                localPosition.y + 0.25f,
                localPosition.z
              ),
              doWhenFinish = {
                val localPosition = renderingModel.localPosition
                ModelAnimations.translateModel(node, localPosition)
              }
            )
          }
        }
        true
      }
    }
  }

  /**
   * Adds a poke-ball object to the AR scene.
   *
   * @param fragment The target [ArFragment] for adding a new pokemon node.
   * @param anchor An anchor for creating a new [AnchorNode].
   * @param pokemonAnchor An anchor of the target pokemon.
   * @param renderable A [Renderable] for setting it to display for the [AnchorNode].
   * @param renderingModel A poke-ball model information for displaying on the scene.
   * @param pokemon A pokemon model information for removing on the scene.
   */
  fun addPokeBallOnScene(
    fragment: ArFragment,
    anchor: Anchor,
    pokemonAnchor: Anchor,
    renderable: Renderable,
    renderingModel: RenderingModel,
    pokemon: RenderingModel,
    doAfterCatch: () -> Unit
  ) {
    val anchorNode = AnchorNode(anchor)
    TransformableNode(fragment.transformationSystem).apply {
      name = renderingModel.name
      localPosition = renderingModel.localPosition
      this.renderable = renderable
      setParent(anchorNode)
      setLookDirection(renderingModel.direction)
      scaleController.minScale = renderingModel.scale
      scaleController.maxScale = renderingModel.scale + 0.05f
      fragment.arSceneView.scene.addChild(anchorNode)
      setOnTapListener { hitTestResult, _ ->
        hitTestResult.node?.let { node ->
          val pokemonPosition = pokemon.localPosition
          val targetPosition = Vector3(
            pokemonPosition.x + getRandomPosition(),
            pokemonPosition.y + getRandomPosition(),
            pokemonPosition.z + getRandomPosition()
          )

          ModelAnimations.translateModel(
            anchorNode = node,
            targetPosition = targetPosition,
            durationTime = 750L
          ) {
            val length = Vector3.subtract(pokemonPosition, targetPosition).length()
            if (length > 0.45) {
              ModelAnimations.translateModel(
                anchorNode = node,
                targetPosition = DEFAULT_POSITION_POKE_BALL,
                durationTime = 0
              )
            } else {
              doAfterCatch()
              pokemonAnchor.detach()
            }
          }

          ModelAnimations.rotateModel(
            anchorNode = node,
            durationTime = 500L
          ) {
            node.setLookDirection(Vector3(0f, 0f, 1f))
          }
        }
      }
      select()
    }
  }

  /**
   * Adds a garden object to the AR scene.
   *
   * @param fragment The target [ArFragment] for adding a new pokemon node.
   * @param anchor An anchor for creating a new [AnchorNode].
   * @param renderable A [Renderable] for setting it to display for the [AnchorNode].
   * @param renderingModel A model information for displaying on the scene.
   */
  fun addGardenOnScene(
    fragment: ArFragment,
    anchor: Anchor,
    renderable: Renderable,
    renderingModel: RenderingModel
  ) {
    val anchorNode = AnchorNode(anchor)
    TransformableNode(fragment.transformationSystem).apply {
      name = renderingModel.name
      localPosition = renderingModel.localPosition
      this.renderable = renderable
      translationController.isEnabled = false
      setParent(anchorNode)
      setLookDirection(renderingModel.direction)
      scaleController.minScale = renderingModel.scale
      scaleController.maxScale = renderingModel.scale + 0.05f
      fragment.arSceneView.scene.addChild(anchorNode)
    }
  }

  /**
   * Returns a random float position between -0.5 to 0.5.
   *
   * @return A random float position between -0.5 to 0.5.
   */
  private fun getRandomPosition(): Float {
    val position = Random.nextFloat()
    return if (position <= 0.5f) {
      position
    } else {
      position - 1
    }
  }
}
