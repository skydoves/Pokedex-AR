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

package com.skydoves.pokedexar.binding

import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.github.florent37.glidepalette.BitmapPalette
import com.github.florent37.glidepalette.GlidePalette
import com.google.android.material.card.MaterialCardView
import com.skydoves.androidribbon.RibbonRecyclerView
import com.skydoves.androidribbon.ribbonView
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.overlay.BalloonOverlayRoundRect
import com.skydoves.pokedexar.R
import com.skydoves.pokedexar.model.Pokemon
import com.skydoves.pokedexar.model.PokemonInfo
import com.skydoves.pokedexar.utils.PokemonUtils
import com.skydoves.pokedexar.utils.SpacesItemDecoration
import com.skydoves.progressview.ProgressView
import com.skydoves.whatif.whatIfNotNull
import com.skydoves.whatif.whatIfNotNullOrEmpty

object ViewBinding {
  @JvmStatic
  @BindingAdapter("toast")
  fun bindToast(view: View, text: String?) {
    text.whatIfNotNullOrEmpty {
      Toast.makeText(view.context, it, Toast.LENGTH_SHORT).show()
    }
  }

  @JvmStatic
  @BindingAdapter("paletteImage", "paletteCard")
  fun bindLoadImagePalette(view: AppCompatImageView, url: String, paletteCard: MaterialCardView) {
    Glide.with(view.context)
      .load(url)
      .listener(
        GlidePalette.with(url)
          .use(BitmapPalette.Profile.MUTED_LIGHT)
          .intoCallBack { palette ->
            val rgb = palette?.dominantSwatch?.rgb
            if (rgb != null) {
              paletteCard.setCardBackgroundColor(rgb)
            }
          }.crossfade(true)
      ).into(view)
  }

  @JvmStatic
  @BindingAdapter("gone")
  fun bindGone(view: View, shouldBeGone: Boolean) {
    view.visibility = if (shouldBeGone) {
      View.GONE
    } else {
      View.VISIBLE
    }
  }

  @JvmStatic
  @BindingAdapter("bindShowingBalloon")
  fun bindShowingBalloon(view: View, pokemon: Pokemon?) {
    pokemon.whatIfNotNull {
      val context = view.context
      if (context is ComponentActivity) {
        Balloon.Builder(context)
          .setLayout(R.layout.layout_custom_balloon)
          .setWidthRatio(0.85f)
          .setHeight(BalloonSizeSpec.WRAP)
          .setIsVisibleArrow(false)
          .setMarginRight(12)
          .setMarginLeft(12)
          .setTextSize(15f)
          .setCornerRadius(8f)
          .setTextColorResource(R.color.white_87)
          .setIconDrawableResource(R.mipmap.ic_launcher)
          .setBackgroundColorResource(R.color.background)
          .setBalloonAnimation(BalloonAnimation.ELASTIC)
          .setOverlayPadding(6f)
          .setOverlayShape(BalloonOverlayRoundRect(12f, 12f))
          .setOnBalloonClickListener { context.finish() }
          .setDismissWhenClicked(false)
          .setDismissWhenTouchOutside(false)
          .setLifecycleOwner(context)
          .build().apply {
            val image = getContentView().findViewById<ImageView>(R.id.image)
            Glide.with(view.context)
              .load(it.getImageUrl())
              .thumbnail(0.25f)
              .into(image)
            val name = getContentView().findViewById<TextView>(R.id.name)
            name.text = it.name
            showAtCenter(view.rootView)
          }
      }
    }
  }

  @JvmStatic
  @BindingAdapter("bindPokemonTypes")
  fun bindPokemonTypes(recyclerView: RibbonRecyclerView, types: List<PokemonInfo.TypeResponse>?) {
    types.whatIfNotNullOrEmpty {
      recyclerView.clear()
      for (type in it) {
        with(recyclerView) {
          addRibbon(
            ribbonView(context) {
              setText(type.type.name)
              setTextColor(Color.WHITE)
              setPaddingLeft(84f)
              setPaddingRight(84f)
              setPaddingTop(2f)
              setPaddingBottom(10f)
              setTextSize(16f)
              setRibbonRadius(120f)
              setTextStyle(Typeface.BOLD)
              setRibbonBackgroundColorResource(
                PokemonUtils.getTypeColor(type.type.name)
              )
            }.apply {
              maxLines = 1
              gravity = Gravity.CENTER
            }
          )
          addItemDecoration(SpacesItemDecoration())
        }
      }
    }
  }

  @JvmStatic
  @BindingAdapter("progressView_labelText")
  fun bindProgressViewLabelText(progressView: ProgressView, text: String?) {
    progressView.labelText = text
  }

  @JvmStatic
  @BindingAdapter("progressView_progress")
  fun bindProgressViewProgress(progressView: ProgressView, value: Int?) {
    if (value != null) {
      progressView.progress = value.toFloat()
    }
  }

  @JvmStatic
  @BindingAdapter("progressView_max")
  fun bindProgressViewMax(progressView: ProgressView, value: Int?) {
    if (value != null) {
      progressView.max = value.toFloat()
    }
  }
}
