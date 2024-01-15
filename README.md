# RandomUser

## General description
Android application on the use of the RandomUser api to display the random user profile.

## Modules
- **App:** Main code of the application.
- **Data:** Data management and storage.
- **Domain:** Business logic.
- **Services:** Management of external services and APIs.

## Architecture
This project employs a clean architecture approach, separating concerns for better testability and ease of understanding. It implements design patterns such as MVVM. It communicates between layers through Interfaces.

## Composition
- **Architecture:** Modular clean architecture.
- **Patterns:** Singleton, MVVM.
- **Components:**
  - DiffUtil for change management in the RecyclerView.
  - Dependency injection with HILT.
  - Pagination control with DataStore
  - View control with Navigation
  - REST calls with Retrofit, OkHttp, and Moshi
  - Layout with Material
  - Image loading with Glide
  - Unit Testing with JUnit4 and MockK
  - Shimmer for data loading

- **Libraries**
  - Dagger HILT
  - Retrofit
  - OkHttp
  - Moshi
  - JetPack DataStore
  - Timber
  - Facebook Shimmer
  - Glide
  - JUnit4
  - MockK

## Getting started
1. Download the project
2. Open it with Android Studio
3. Access your "local.properties" file (if it doesn't appear, create it in the main path of the project) and add your Google Maps API KEY with this format:
   **GOOGLE_MAPS_API_KEY=XXXXXXXXXXXXXXXXXXXXXXXXXXX**
4.  Run the application on an emulator or physical device.

## License
This project is licensed under Apache 2.0.

## Contact
This project has been developed by [Julien Quiévreux](https://www.linkedin.com/in/julien-qui%C3%A9vreux-7a85863b/).

## Acknowledgements
The data has been extracted from the [Random User API](https://randomuser.me/) API.


For more details, visit the [GitHub repository](https://github.com/juque-jqx/RandomUserMe).
