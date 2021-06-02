<h1 align="center">Pokedex-AR</h1>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
  <a href="https://android-arsenal.com/api?level=24"><img alt="API" src="https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat"/></a>
  <a href="https://github.com/skydoves/Pokedex-AR/actions"><img alt="Build Status" src="https://github.com/skydoves/Pokedex-AR/workflows/Android%20CI/badge.svg"/></a> 
  <a href="https://github.com/skydoves"><img alt="Profile" src="https://skydoves.github.io/badges/skydoves.svg"/></a> 
</p>

<p align="center">  
Pokedex-AR is a small demo application based on AR, modern Android application tech-stacks, <br>and MVVM architecture. This project focuses on ARCore and SceneForm. <br>Also fetching data from the network and integrating persisted data from the database via repository pattern.
</p>
</br>

<p align="center">
<img src="/previews/screenshot.png"/>
</p>

## Download
Go to the [Releases](https://github.com/skydoves/Pokedex-AR/releases) to download the latest APK.


<img src="/previews/preview.gif" align="right" width="32%"/>

## Tech stack & Open-source libraries
- Minimum SDK level 24.
- [Kotlin](https://kotlinlang.org/) based, [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) + [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) for asynchronous.
- [ARCore](https://github.com/google-ar/arcore-android-sdk) - ARCore SDK for Android.
- [SceneForm](https://github.com/google-ar/sceneform-android-sdk) - Sceneform SDK for Android.
- Hilt for dependency injection.
- JetPack
  - Lifecycle - dispose of observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - Room Persistence - construct a database using the abstract layer.
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - Repository pattern.
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.
- [Sandwich](https://github.com/skydoves/Sandwich) - construct lightweight http API response and handling error responses.
- [Moshi](https://github.com/square/moshi/) - A modern JSON library for Kotlin and Java.
- [Glide](https://github.com/bumptech/glide), [GlidePalette](https://github.com/florent37/GlidePalette) - loading images.
- [WhatIf](https://github.com/skydoves/whatif) - checking nullable object and empty collections more fluently.
- [Bundler](https://github.com/skydoves/bundler) - Android Intent & Bundle extensions that insert and retrieve values elegantly.
- [Balloon](https://github.com/skydoves/balloon) -  A lightweight popup like tooltips, fully customizable with an arrow and animations.
- [Timber](https://github.com/JakeWharton/timber) - logging.
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components like ripple animation, cardView.
- Custom Views
  - [Rainbow](https://github.com/skydoves/rainbow) - An easy way to apply gradations and tinting for Android.
  - [AndroidRibbon](https://github.com/skydoves/androidribbon) - A simple way to implement a  beautiful ribbon with the shimmering on Android.
  - [ProgressView](https://github.com/skydoves/progressview) - A polished and flexible ProgressView, fully customizable with animations.

## MAD Score
![summary](https://user-images.githubusercontent.com/24237865/102298434-77a5db00-3f94-11eb-9ae5-5fc72aa60533.png)
![kotlin](https://user-images.githubusercontent.com/24237865/102298432-7674ae00-3f94-11eb-8b9b-b3b9f4ff4c22.png)

## Architecture
Pokedex-AR is based on MVVM architecture and a repository pattern.

![architecture](https://user-images.githubusercontent.com/24237865/77502018-f7d36000-6e9c-11ea-92b0-1097240c8689.png)

## 3D Model Licenses
 Creative Commons licenses provide a standard way for content creators to grant someone else permission to use their work. If you allow remixing, your content will be published under a CC-BY 3.0 license. These creations are then accessible to other Poly users for use, even commercially, in their own creations, provided they give attribution to the original author.
 
  - [Tipatat Chennavasin](https://poly.google.com/user/8ri62AdjHrC) - Pokemons and Poke-ball.
  - [Poly by Google](https://poly.google.com/user/4aEd8rQgKu2) - Garden.

## Open API

<img src="https://user-images.githubusercontent.com/24237865/83422649-d1b1d980-a464-11ea-8c91-a24fdf89cd6b.png" align="right" width="21%"/>

Pokedex-AR using the [PokeAPI](https://pokeapi.co/) for constructing RESTful API.<br>
PokeAPI provides a RESTful API interface to highly detailed objects built from thousands of lines of data related to Pokémon.

## Find this repository useful? :heart:
Support it by joining __[stargazers](https://github.com/skydoves/Pokedex-AR/stargazers)__ for this repository. :star: <br>
And __[follow](https://github.com/skydoves)__ me for my next creations! 🤩

# License
```xml
Designed and developed by 2020 skydoves (Jaewoong Eum)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
