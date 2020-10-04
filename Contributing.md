# Contributing

You're welcomed to submit issues and pull requests as long as you adhere to Github community guideline. Any form of contribution is welcomed,  from typo error to submitting a new feature.

## Setting up Development Environment

You would need to go through a couple of configuration to setup development environment. Please **do note stage these configurations** as they are meant for your local environment only.

### Setup Google Play Services

We uses Google Play Services for Firebase Crashlytics reporting as well as for basic analytics. Since we cannot provide you access to our Firebase console, you will need to generate a `play-services.json` on your own.

1. Add two apps with `com.popstack.mvoter2015.debug` and `com.popstack.mvoter2015`  to your firebase console
2. Add the `play-services.json` to app root folder

Make sure you **DO NOT ** stage these into git. In most cases, it should automatically ignore it without you having to do extra work.

### Setup local.properties

If you want to test with release configs, you need to add these 6 values into your `local.properties`.

```
RELEASE_KEYSTORE_PATH=
RELEASE_KEYSTORE_PASSWORD=
RELEASE_KEY_ALIAS=
RELEASE_KEY_PASSWORD=
DEVELOPMENT_API_SECRET={USE FAKE NETWORK INSTEAD, SO YOU CAN PUT ANYTHING HERE}
RELEASE_API_SECRET={USE FAKE NETWORK INSTEAD, SO YOU CAN PUT ANYTHING HERE}
```

For `API_SECRET`, you can leave any string,  use the fake network module instead (read below). Be sure that this changes is **not staged** as well.

### Setup Fake Network

Since we can't share the prod environment as of now, a fake network module is added to the project

1. Open `data/android/build.gradle.kts`
2. Uncomment `debugApi(project(":data:fakenetwork"))`
3. comment `debugApi(project(":data:network"))`

Make sure this change is **not staged**.

### Building

After doing those, run `./gradlew assemble` and the project should be built correctly. Happy coding!