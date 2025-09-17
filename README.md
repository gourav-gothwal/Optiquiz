# 🧠 OptiQuiz

OptiQuiz is a modern Kotlin-based quiz application that provides users
with engaging quizzes fetched in real-time from an external API. The app
delivers an interactive and dynamic learning experience with a clean and
minimal UI.

------------------------------------------------------------------------

## ✨ Features

-   📡 **API-powered Quizzes** -- Fetches quiz questions from an
    external trivia API.
-   🎨 **Modern UI** -- Simple and user-friendly interface.
-   ⏳ **Timed Questions** -- Challenge yourself with time-bound quizzes
    (optional).
-   🏆 **Score Tracking** -- Displays scores and results at the end of
    the quiz.
-   🔄 **Randomized Questions** -- Ensures a fresh experience every
    time.

------------------------------------------------------------------------

## 🛠️ Tech Stack

-   **Language**: Kotlin
-   **Architecture**: MVVM (Model-View-ViewModel)
-   **Networking**: Retrofit / OkHttp for API calls
-   **Dependency Injection**: Hilt (optional)
-   **UI**: XML (or Jetpack Compose if used)
-   **Asynchronous**: Coroutines + Flow

------------------------------------------------------------------------

## 🚀 Getting Started

### Prerequisites

-   Android Studio (latest stable version)
-   Minimum SDK: 21+
-   Kotlin 1.9+

### Installation

1.  Clone the repository

    ``` bash
    git clone https://github.com/gourav-gothwal/optiquiz.git
    cd optiquiz
    ```

2.  Open the project in **Android Studio**.

3.  Sync Gradle dependencies.

4.  Run the app on an emulator or physical device.

------------------------------------------------------------------------

## 📂 Project Structure

    OptiQuiz/
     ├── data/            # Repository & API services
     ├── model/           # Data models (Quiz, Question, etc.)
     ├── ui/              # Activities/Fragments/ViewModels
     ├── utils/           # Helper classes (constants, extensions)
     ├── di/              # Dependency injection setup
     └── build.gradle     # Gradle configs

------------------------------------------------------------------------

## 📡 API Reference

This project uses a quiz API (e.g., [Open Trivia
DB](https://opentdb.com/)).
- Example endpoint:
`https://opentdb.com/api.php?amount=10&type=multiple`

------------------------------------------------------------------------

## 📸 Screenshots

(Add screenshots of your app here once ready)

------------------------------------------------------------------------

## 🤝 Contributing

Contributions are welcome! Please fork the repo and create a pull
request.

------------------------------------------------------------------------

## 📜 License

This project is licensed under the MIT License -- see the
[LICENSE](LICENSE) file for details.
