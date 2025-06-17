# NIT3213 Mobile Application Project

## Overview

This is a comprehensive Android application built for the NIT3213 assignment, showcasing modern mobile development practices. The app features a dynamic, API-driven architecture that can adapt to various data structures without hardcoded dependencies.

## 🚀 Quick Start Guide

### Prerequisites
- **Android Studio**: Flamingo (2022.3.1) or newer
- **Java**: JDK 11 or higher
- **Android SDK**: API level 30 or higher
- **Git**: For version control

### 📱 How to Run the Application

1. **Clone the Repository**
   ```bash
   git clone https://github.com/kid0733/MobileDevAssignment.git
   cd MobileDevAssignment
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Click "Open an Existing Project"
   - Navigate to the cloned directory and select it
   - Wait for Gradle sync to complete

3. **Configure the Project**
   - Ensure you have Android SDK API 30+ installed
   - Let Gradle download all dependencies automatically
   - No additional configuration needed

4. **Run the Application**
   - Connect an Android device (API 30+) OR start an emulator
   - Click the "Run" button (green triangle) in Android Studio
   - Select your target device
   - The app will build and install automatically

5. **Test Login Credentials**
   - Username: `Siddhant`
   - Password: `s8091542`
   - Use Sydney endpoint for authentication

### 🛠️ Build Commands (Optional)
```bash
# Clean build
./gradlew clean

# Build debug APK
./gradlew assembleDebug

# Run tests
./gradlew test

# Install on connected device
./gradlew installDebug
```

## 🎯 Core Objectives

The application demonstrates expertise in:
- RESTful API integration and data handling
- Contemporary mobile UI/UX design
- Professional Android development standards
- Flexible content management systems

## 🏗️ System Architecture

Built using **MVVM (Model-View-ViewModel)** pattern featuring:
- **ViewBinding** for type-safe view access
- **ViewModel** for business logic separation
- **LiveData** for reactive programming
- **Hilt** for dependency injection
- **Retrofit** for network operations
- **Kotlin Coroutines** for async processing

## ✨ Application Features

### Core Screen Components:

1. **Authentication Screen**
   - Secure user login with credential validation
   - Real-time error feedback and handling
   - Professional, responsive interface

2. **Data Dashboard**
   - Dynamic RecyclerView for entity display
   - API-driven content rendering
   - Interactive navigation to detailed views
   - Smooth loading states

3. **Entity Detail View**
   - Comprehensive information display
   - Flexible property rendering
   - Elegant card-based presentation

## 🌐 API Configuration

**Server Base:** `https://nit3213api.onrender.com/`

### Login Endpoints
```
POST /footscray/auth
POST /sydney/auth  
POST /ort/auth
```

**Authentication Payload:**
```json
{
  "username": "StudentFirstName",
  "password": "sStudentNumber"
}
```

**Success Response:**
```json
{
  "keypass": "accessToken"
}
```

### Data Retrieval
```
GET /dashboard/{accessToken}
```

**Response Format:**
```json
{
  "entities": [
    {
      "field1": "data1",
      "field2": "data2", 
      "description": "Entity description"
    }
  ],
  "entityTotal": 5
}
```

## 🔧 Technical Implementation

### Key Innovation: Dynamic Entity System
```kotlin
data class Entity(val properties: Map<String, Any>)
```

**Why this is brilliant:**
- **Universal Data Adapter**: Works with any JSON structure
- **Future-Proof**: API changes don't break the app
- **Zero Hardcoding**: No predefined field assumptions
- **Dynamic UI**: Interface adapts to data structure

### Core Architecture Components

#### Data Layer
- **ApiService**: Retrofit interface for HTTP calls
- **MainRepository**: Single source of truth for data
- **NetworkModule**: Hilt dependency injection setup
- **Entity Models**: Dynamic and traditional data models

#### Business Logic Layer
- **LoginViewModel**: Authentication state management
- **DashboardViewModel**: Entity list and loading states
- **LiveData Observers**: Reactive UI updates

#### UI Layer
- **LoginActivity**: Authentication interface
- **DashboardActivity**: Entity list with RecyclerView
- **DetailActivity**: Individual entity display
- **EntityAdapter**: Dynamic list adapter

### Network Infrastructure
- **OkHttp**: HTTP client with logging and DNS fallback
- **Retrofit**: Type-safe API calls
- **Gson**: JSON parsing with custom Entity deserializer
- **Coroutines**: Background processing

## 📋 Project Structure

```
app/src/main/java/com/example/nit3213app/
├── data/
│   ├── api/ApiService.kt
│   └── model/
│       ├── Entity.kt           # Core innovation
│       ├── LoginRequest.kt
│       ├── LoginResponse.kt
│       └── DashboardResponse.kt
├── di/NetworkModule.kt         # Dependency injection
├── repository/MainRepository.kt
├── ui/
│   ├── login/LoginActivity.kt
│   ├── dashboard/
│   │   ├── DashboardActivity.kt
│   │   └── EntityAdapter.kt
│   └── detail/DetailActivity.kt
├── viewmodel/
│   ├── LoginViewModel.kt
│   └── DashboardViewModel.kt
└── NIT3213Application.kt      # Hilt setup
```

## 📋 Feature Implementation Status

### ✅ Authentication Module
- [x] Username/password input fields
- [x] Student credential format support
- [x] Dynamic endpoint selection
- [x] Comprehensive error management
- [x] Successful login navigation

### ✅ Dashboard Module
- [x] RecyclerView entity display
- [x] Token-based API authentication
- [x] Summary view (excluding descriptions)
- [x] Detail navigation functionality
- [x] Loading and error state handling

### ✅ Detail Module
- [x] Complete entity information display
- [x] Description field inclusion
- [x] User-friendly layout design
- [x] Professional information presentation

### ✅ Technical Excellence
- [x] MVVM architecture implementation
- [x] Hilt dependency injection
- [x] ViewBinding for type safety
- [x] Reactive programming with LiveData
- [x] Professional error handling
- [x] Modern UI design

## 🎨 Design Philosophy

- **Material Design 3** implementation
- **Dark theme** with neon accents
- **Adaptive layouts** for different screen sizes
- **Smooth animations** and loading states
- **Intuitive navigation** and error messaging
- **Professional visual hierarchy**

## 🌟 Key Innovations

### 1. Universal Entity System
- **Dynamic JSON Handling**: Works with any API response format
- **Runtime Field Discovery**: No hardcoded field names
- **Type-Safe Conversion**: Handles strings, numbers, booleans
- **Automatic UI Adaptation**: Interface adjusts to data structure

### 2. Professional Architecture
- **Clean MVVM**: Clear separation of concerns
- **Dependency Injection**: Modular and testable code
- **Reactive Programming**: UI automatically updates
- **Error Boundaries**: Comprehensive failure handling

### 3. Production-Ready Features
- **Network Resilience**: DNS fallback and retry logic
- **Lifecycle Awareness**: Prevents memory leaks
- **State Management**: Survives configuration changes
- **Security**: Proper authentication token handling

## 🧪 Testing

Current test setup includes:
- Basic unit test structure
- Instrumented test framework
- **Note**: Comprehensive tests would include ViewModel, Repository, and Entity model validation

## 📝 Technical Notes

This project demonstrates:
- **Industry-standard patterns**: MVVM, Repository, Dependency Injection
- **Modern Android development**: ViewBinding, LiveData, Coroutines
- **Clean architecture principles**: Separation of concerns, SOLID principles
- **Professional UI/UX**: Material Design, responsive layouts
- **Scalable codebase**: Easy to extend and maintain

## 🐛 Troubleshooting

### Common Issues:
1. **Build Errors**: Ensure Android SDK API 30+ is installed
2. **Network Issues**: Check internet connection and API availability
3. **Login Failures**: Verify credentials: Username="Siddhant", Password="s8091542"
4. **Gradle Sync**: Clean and rebuild if dependencies fail to resolve

### Debug Steps:
1. Check Logcat for network and application logs
2. Verify API endpoints in NetworkModule.kt
3. Ensure proper emulator/device API level (30+)

---

## 👨‍💻 Project Information

**Developer**: Siddhant Singh Karki  
**Student ID**: s8091542  
**Course**: NIT3213 - Android Application Development  
**Assignment**: Final Project - Dynamic Mobile Application  
**Repository**: https://github.com/kid0733/MobileDevAssignment

## 📄 License

This project is for educational purposes as part of the NIT3213 coursework.

---

## 🤝 Acknowledgments

- VU Android Development Course Materials
- Android Architecture Guidelines
- Material Design 3 Documentation