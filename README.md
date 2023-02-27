## Important notes: The API is not working!

- The API isnt working: if you want to understand how the app works, go to the app module and run the instrumented tests: [UI tests](https://github.com/fredelinhares/nubank/tree/master/app/src/androidTest/java/com/nubank/takehomeevaluation)
- The UI is not made in Jetpack Compose.
- It is extremely important to point out that the development of a layout with an advanced visual aspect was not taken into account.
- The division of modules did not take into account the scalability factor. In a real project, we could create, for example, a multi repository app,
where we could have, for example:
   - A data repository, following the "single source of truth (SSOT)" model: https://en.wikipedia.org/wiki/Single_source_of_truth
   - A separate repository for each feature of the project - for example, the urlshortener module. Such feature modules would, in the end, be incorporated into a parent application via maven/gradle, applying a dependency management strategy - BOM or Bill Of Materials (example at, https://firebase.blog/posts/2020/11/dependency -management-ios-android).
   - A repository for managing SDK infrastructure like firebase.
   - A repository for managing skds for analytics...

## What is this application?

Urlshortener is a small Android app that allows you to shorten urls and display a history of recently shortened links to your favorite websites.

This app consists of only one screen, which has:

- A text entry where the user can type the URL of the site to shorten;
- A button that will trigger the action of sending this link to the service;
- A list with the recently shortened links/aliases.  

## Technologies/Architecture/Principles:

- [x] Platform - Android: https://developer.android.com/
- [x] Language - Kotlin: https://kotlinlang.org/
- [x] Single activity - Presentation by Ian Lake -> 'Single activity - Why, when, and how (Android Dev Summit '18)': https://www.youtube.com/watch?v=2k8x8V77CrU&ab_channel=AndroidDevelopers
- [x] Multi-Module - Presentation by Yigit Boyar, Florina Mutenescu -> 'Build a modular Android app architecture (Google I/O'19)': https://www.youtube.com/watch?v=PZBg5DIzNww&ab_channel=AndroidDevelopers
- [x] Navigation component: https://developer.android.com/guide/navigation/navigation-getting-started
- [x] Koin -DI framework: https://insert-koin.io/
- [x] MVVM: https://developer.android.com/topic/architecture
- [x] Clean architecture: https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html
- [x] Clean code: https://www.oreilly.com/library/view/clean-code-a/9780136083238/
- [x] Coroutines: https://kotlinlang.org/docs/coroutines-guide.html
- [x] Retrofit: https://square.github.io/retrofit/
- [x] S.O.L.I.D: https://en.wikipedia.org/wiki/SOLID  

## Tests:
- [x] Unit tests - MockK: https://github.com/mockk/mockk
- [x] Component testing with - Robolectric: http://robolectric.org/
- [x] Instrumented tests with - Espresso: https://developer.android.com/training/testing/espresso
 
## Code organization

The application is divided into 03 modules:

- [app](https://github.com/fredelinhares/url-shortener/tree/master/app): responsible for running the UI tests (in an eventual deploy, this module - and all the code contained in it - will not will be deployed to production).

- [core](https://github.com/fredelinhares/url-shortener/tree/master/core):
  - Infrastructure for communication with the API (ApiClientBuilder, RequestManager), exceptions for communication with the server, etc...
  - Fragment state management: BaseViewModel, ViewState.

- [urlshortener](https://github.com/fredelinhares/url-shortener/tree/master/urlshortener): module responsible for implementing the feature. Contains the canvas (UrlShortenerListFragment) with which the user interacts.
