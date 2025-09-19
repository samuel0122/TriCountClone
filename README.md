# TriCount Clone

*A reduced version of the TriCount app built to practice Jetpack Compose, MVVM, and real-time UI updates with a clean and scalable architecture.*

This project is a bill-splitting app inspired by TriCount, designed to master Jetpack Compose, real-time UI updates, and maintainable code practices using MVVM and Clean Architecture.


## 📋 Features

- 👥 **Group & Participant Management**: Create tricount groups and add participants.
- ➕ **Add, Edit, and Delete Expenses**: Manage group expenses.
- 🔄 **Expense Sharing**: Calculate each participant’s share dynamically.
- 🎨 **Jetpack Compose UI**: Reactive, modern, and declarative UI components.
- ⚡ **Real-Time UI Updates**: StateFlow-powered reactive UI with Jetpack Compose.
- 💾 **Persistence with Room**: Local database with DAOs, entities, and relations.
- 🧱 **Clean Architecture**: Clean separation in layers: Domain, Data and View.
- 💉 **Dependency Injection**: Powered by Dagger Hilt for modular and testable code.
- 🚀 **Navigation Component**: Structured and scalable navigation across screens.


## 🛠️ Tech Stack

- **Kotlin**: 100% Kotlin-based code.
- **Jetpack**:
  - Navigation Component.
  - DataStore for managing preferences.
  - ViewModel and LiveData for reactive UI updates.
  - Jetpack Compose for UI building.
- **Dagger Hilt**: Dependency Injection.
- **Room**: For local database management via an abstraction layer over SQLite.


## 🚀 Getting Started

### Prerequisites
1. Android Studio (latest stable version recommended)
2. Android SDK 21+


### Setup
1. [ ] Clone the repository:
    ```bash
    git clone https://github.com/samuel0122/TriCountClone.git
    cd TriCountClone
    ```
2. [ ] Open the project in Android Studio.
3. [ ] Sync Gradle to download dependencies.
4. [ ] Run the app on a physical or virtual device.


## 📂 Project Structure

```
📂 app/
├── 📂 core/                      # Core types
├── 📂 data/                      # Data layer (repositories, database)
│   ├── 📂 database/              # Room database, DAOs, entities
│   ├── 📂 preferences            # Manages preferences using DataStore (e.g. logged user ID)
│   ├── 🗒️ TricountRepository     # Repository to manage tricounts data
│   ├── 🗒️ UserRepository         # Repository to manage users data
│   ├── 🗒️ ParticipantRepository  # Repository to manage tricount participants data
│   ├── 🗒️ ExpenseRepository      # Repository to manage expenses data
│   ├── 🗒️ ExpenseShareRepository # Repository to manage expense shares data
├── 📂 di/                        # Dependency injection modules
├── 📂 domain/                    # Domain layer (Use Cases, models and mappers)
├── 📂 ui/                        # View layer (Screens, navigation, components, widgets)
│   ├── 📂 components             # Reusable composables (Cards, Buttons, TextFields)
│   ├── 📂 dialogs                # Dialogs
│   ├── 📂 model                  # UI Model classes
│   ├── 📂 navigation             # Route definitions and NavHost setup
│   ├── 📂 screens                # Screens and ViewModels
│   ├── 📂 theme                  # Compose theming
│   ├── 📂 widgets                # Higher-level UI widgets (lists, items, forms)
├── 📂 utils/                     # Utility classes and Room type converters
├── 🗒️ TriCountCloneApp           # Application class for global injection
├── 🗒️ MainActivity               # Application entry point and navigation host
```


## 📸 Screenshots


## 🧑‍💻 Author

- [@samuel0122](https://www.github.com/samuel0122)
