# Kotlin Multiplatform app template

[![official project](http://jb.gg/badges/official.svg)](https://confluence.jetbrains.com/display/ALL/JetBrains+on+GitHub)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

This is a basic Kotlin Multiplatform app template for Android, iOS and HarmonyOS NEXT. It includes shared business logic and data handling, and native UI implementations using Jetpack Compose, SwiftUI and [ArkUI](https://developer.huawei.com/consumer/cn/arkui).

> The template is also available [with shared UI written in Compose Multiplatform](https://github.com/kotlin/KMP-App-Template).
>
> The [`amper` branch](https://github.com/Kotlin/KMP-App-Template-Native/tree/amper) showcases the same project configured with [Amper](https://github.com/JetBrains/amper).
>
> The [`harmony` branch](https://github.com/Kotlin/KMP-App-Template-Native-Harmony/tree/harmony) showcases the same project based on Kotlin/JS for targeting [Huawei HarmonyOS NEXT](https://developer.huawei.com/consumer/cn/discover).

![Screenshots of the app](images/screenshots.png)

### HarmonyOS NEXT
#### Set up an environment
- [DevEco Studio](https://developer.huawei.com/consumer/cn/doc/harmonyos-guides-V5/ide-tools-overview-V5)
- [Command Line Tools](https://developer.huawei.com/consumer/cn/doc/harmonyos-guides-V5/ide-commandline-get-V5)
#### Run harmonyApp
1. Open the `KMP-App-Template-Native-Harmony` project in Android Studio or Fleet.
    ```
    ./gradlew :shared:jsDevelopmentExecutableCompileSync
    ```
2. Open the `harmonyApp` project in DevEco Studio.
   ![Screenshots of the run_harmonyApp](images/run_harmonyApp.png)

### Technologies

The data displayed by the app is from [The Metropolitan Museum of Art Collection API](https://metmuseum.github.io/).

The app uses the following multiplatform dependencies in its implementation:

- [Ktor](https://ktor.io/) for networking
- [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) for JSON handling
- [Koin](https://github.com/InsertKoinIO/koin) for dependency injection
- [KMP-ObservableViewModel](https://github.com/rickclephas/KMP-ObservableViewModel) for shared ViewModel implementations in common code
- [KMP-NativeCoroutines](https://github.com/rickclephas/KMP-NativeCoroutines)

> These are just some of the possible libraries to use for these tasks with Kotlin Multiplatform, and their usage here isn't a strong recommendation for these specific libraries over the available alternatives. You can find a wide variety of curated multiplatform libraries in the [kmp-awesome](https://github.com/terrakok/kmp-awesome) repository.

And the following Android-specific dependencies:

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Navigation component](https://developer.android.com/jetpack/compose/navigation)
- [Coil](https://github.com/coil-kt/coil) for image loading

Thanks:

- [kotlin-ohos-sample](https://github.com/kotlin-for-ohos/kotlin-ohos-sample)
