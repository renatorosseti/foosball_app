# foosball_app

### Structure of the code ###
Simple Android Application written in Kotlin.
This project follows Clean Architecture with MVVM with Clean Architecture Design

# Main libraries used

- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Rxjava](https://github.com/ReactiveX/RxJava) - For asynchronous and more..
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
    - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes.
    - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
    - [Data Binding](https://developer.android.com/topic/libraries/data-binding) - Used for data binding
    - [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started) - Used it for the navigation from one fragment to another fragments.
- [Dependency Injection](https://developer.android.com/training/dependency-injection)
    - [Dagger2](https://dagger.dev/) - Standard library to incorporate Dagger dependency injection into an Android application.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Material Components for Android](https://github.com/material-components/material-components-android) - Modular and customizable Material Design UI components for Android.
- [Junit](https://junit.org/) - For Unit Testing
- [MokK](https://github.com/mockk/mockk) - For mocking in Unit Testing

# Modules
* `data/` : contains the code to access to the data (repository pattern)
* `domain/` : contains the business logic and the usecases
* `app/` : Presentation layer, contains the UI 

# Application
This application has three screens. On the first screen shows the list of players and when clicking on the item
go to the details of the player. On the second screen shows the player details with the list of games and when clicking on the item
go to the details of the game. On third screen shows the game details, it is possible to edit/create games and create a new player as well.