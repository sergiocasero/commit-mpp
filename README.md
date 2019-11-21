#Commit 2019 Kotlin multiplatform app

Hello and welcome to the CommitConf 2019 (no official) app.

This app has been developed with kotlin multiplatform. Check out the app on the stores:

Google Play: https://play.google.com/store/apps/details?id=com.sergiocasero.commit

iOS: https://apps.apple.com/us/app/commit-2019-no-oficial/id1487080658?l=es&ls=1

#Android

That's simple, create an Android task on IntelliJ, assign the "app" module to it and then, run like a normal Android App

#iOS

1. Open a terminal, and go to `iosApp` folder

2. Run `pod install`

3. perform a `./gradlew build` command. After that, the project will generate the app.framework file. Now, you can open the xCode project (under `iosApp` folder)

4. Open the `iosApp.xcworkspace` and run the iOS project.

Important: Open the `xcworkspace` file is mandatory because this project uses cocoapods 

#frontend

1. Checkout "more_platforms" branch

2. Sync gradle

3. Execute `./gradlew common:jsBrowserRun -- continuous` in order to have instant reload

4. Visit `localhost:8080`

#desktop

1. Checkout "more_platforms" branch

2. Sync gradle

3. Execute `./gradlew common:run`

4. A JavaFx window will be opened

#backend

That's simple, execute `./gradlew backend:run` and that's all.

#License

```
Copyright 2019 Sergio Casero & Daniel Llanos

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.```