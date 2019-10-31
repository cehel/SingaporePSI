# SingaporePSI

## Architecture

This app is using MVVM architecure implemented with android architecture components including LiveData

## Implementation details

Since it is a small project, no dependency injection framework was used. However for a larger project it is recommended to use a DI-framework such as dagger2 or kodein. This will facilitate testing and separation of concerns
Currently the app only loads the data from the API and does not cache or store it locally. However the structure of the application allows an easy extension to just implement a new PSIDataSource and use it.

## Libraries used

- Android Architecture Components
- Retrofit
- Mockito
- mockk
- expresso
- JUnit5
- Google Maps

## Future work
- Add caching or save the data locally using Room
- Introduce dependency injection framework
- Test UI displayed on the map
- There is no SplashScreen image