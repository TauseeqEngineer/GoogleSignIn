# Google Sign-In with Firebase Authentication

This Android project demonstrates how to integrate **Google Sign-In** with **Firebase Authentication** for user login. It allows users to sign in using their Google account, and after successful authentication, the app logs the user in via Firebase Authentication.

---

## Features

- **Google Sign-In**: Users can log in using their Google account.
- **Firebase Authentication**: Handles user authentication securely using Firebase after Google Sign-In.
- **Error Handling**: Displays appropriate error messages for failed sign-ins or invalid credentials.
- **Session Management**: Automatically manages the user session using Firebase, maintaining authentication across app launches.

---

## Prerequisites

Before you begin, ensure you have the following installed and configured:

### 1. **Android Studio**:
   - Download and install the latest version of [Android Studio](https://developer.android.com/studio).

### 2. **Firebase Account**:
   - You need a Firebase project to use Firebase Authentication.
   - Go to the [Firebase Console](https://console.firebase.google.com/), and create a new project if you don’t already have one.
   - Add the client_id from google_services.gson file into strings.xml

### 3. **Google Sign-In Setup in Firebase**:
   - In your Firebase console, enable **Google Sign-In** as a sign-in method under **Authentication > Sign-in method**.
   - Download the `google-services.json` configuration file from Firebase and add it to the `app/` directory of your Android project.

### 4. **Add Firebase SDK to Your Project**:
   - Open your `build.gradle` files and add the necessary Firebase and Google Play services dependencies.


