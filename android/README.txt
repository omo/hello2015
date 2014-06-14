
## Hello

 * Install Android Studio 0.5.3, then update it to 0.6.1
 * Create a sample project, build error 
   * Fix tool version
   * Install newer SDKs through SDK manager as suggested
   * Let the app update Maven index
 * Create emulator image

## Robolectic

 * http://robolectric.org/
 * https://github.com/robolectric/robolectric-gradle-plugin
 * https://github.com/robolectric/deckard-gradle

 * Rewrite app.iml to hide SDK-provided library by Robolectic
   * https://github.com/robolectric/deckard-gradle
 * BAD: We CANNOT run the Robolectic test through IDE so far. Use the shell.
   (Probably possible, but for a beginner it is not worth spending time for that.)
 * BAD: Robolectic doesn't support API Level 19 yet.
   * Workaround: Use @Config annotation. http://stackoverflow.com/questions/20541630/robolectric-does-not-support-api-level

```
    $ ./gladlew test
```

Questions:

 * How can I run the test on the device?
