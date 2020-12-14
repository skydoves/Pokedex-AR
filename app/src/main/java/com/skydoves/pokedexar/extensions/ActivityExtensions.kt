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

import android.view.Window
import android.view.WindowManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

@Suppress("DEPRECATION")
fun AppCompatActivity.applyFullScreenWindow() {
  requestWindowFeature(Window.FEATURE_NO_TITLE)
  window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
}

@Suppress("UNCHECKED_CAST")
fun <T : Fragment> AppCompatActivity.findFragmentAs(@IdRes resource: Int): T {
  return supportFragmentManager.findFragmentById(resource) as? T
    ?: throw IllegalArgumentException("can not find the id resource: $resource")
}
