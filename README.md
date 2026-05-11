
# **UnvNews (Universal News)**

**UnvNews** is a feature-rich Android application designed for modern news consumption. It allows users to browse real-time news across various categories, search for specific topics, and save articles for offline reading.

Built with **Java** and following the **MVVM (Model-View-ViewModel)** architectural pattern, this project demonstrates clean code practices, reactive UI, and robust local data persistence.

## **🚀 Features**

* **Real-time News:** Fetches the latest headlines using a REST API.
* **MVVM Architecture:** Ensures a decoupled and testable codebase.
* **Offline Support:** Uses **Room Database** to store favorite articles locally.
* **Dynamic Search:** Search for news articles globally with a responsive UI.
* **In-App Browsing:** View full news stories without leaving the app using an integrated WebView.
* **Category Filtering:** Easily navigate through Business, Technology, Sports, and more.

## **🛠️ Tech Stack**

* **Language:** Java
* **Architecture:** MVVM
* **Networking:** Retrofit & OkHttp
* **Local Database:** Room Persistence Library
* **UI Components:** * Jetpack ViewModel & LiveData
* RecyclerView with Custom Adapters
* Lottie Animations for a modern feel
* Material Design Components



## **📂 Project Structure**

```text
com.unvnews.unvnews/
├── data/
│   ├── local/          # Room Database, DAOs, and Entities
│   ├── remote/         # Retrofit API Interface
│   └── repository/     # Single source of truth for data
├── viewmodel/          # ArticleViewModel (Business Logic)
├── ui/                 # Activities, Adapters, and Listeners
└── utils/              # Constants and Helper classes

```

## **📸 Screenshots**

*(You can add your screenshot links here later)*

## **⚙️ Getting Started**

1. **Clone the repository:**
```bash
git clone https://github.com/FaizanSayed404/UnvNews-Mobile-Application.git

```


2. **Open in Android Studio:**
Import the project and let Gradle sync.
3. **API Key:**
Get an API key from [NewsAPI.org](https://newsapi.org/) and add it to your `Constants.java` file.
4. **Build and Run:**
Deploy to a physical device or emulator.

---

**Developed by [Sayed Faizan Akhter**](https://www.google.com/search?q=https://github.com/FaizanSayed404)
