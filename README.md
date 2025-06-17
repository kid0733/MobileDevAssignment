# NIT3213 Mobile Application Project

## Overview

This is a comprehensive Android application built for the NIT3213 assignment, showcasing modern mobile development practices. The app features a dynamic, API-driven architecture that can adapt to various data structures without hardcoded dependencies.

## 🎯 Core Objectives

The application demonstrates expertise in:
- RESTful API integration and data handling
- Contemporary mobile UI/UX design
- Professional Android development standards
- Flexible content management systems

## 🏗️ System Architecture

Built using **MVVM (Model-View-ViewModel)** pattern featuring:
- **Data Binding** for efficient view management
- **ViewModel** for business logic separation
- **LiveData** for reactive programming
- **Hilt** for dependency management
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
  \"username\": \"StudentFirstName\",
  \"password\": \"sStudentNumber\"
}
```

**Success Response:**
```json
{
  \"keypass\": \"accessToken\"
}
```

### Data Retrieval
```
GET /dashboard/{accessToken}
```

**Response Format:**
```json
{
  \"entities\": [
    {
      \"field1\": \"data1\",
      \"field2\": \"data2\", 
      \"description\": \"Entity description\"
    }
  ],
  \"entityTotal\": 5
}
```

## 🔧 Technical Implementation

### Adaptive Data Management
- **Flexible Entity System**: Handles any JSON structure using key-value mapping
- **Dynamic UI Generation**: Interface adapts based on API response format
- **Multi-endpoint Support**: Configurable authentication based on location

### Core Components

#### Entity Data Model
```kotlin
data class Entity(val properties: Map<String, Any>)
```
- Stores information as flexible key-value pairs
- Accommodates any JSON response structure
- Includes utility methods for data access

#### Application Flow
1. **Authentication**: Login → Validate → Receive Token
2. **Data Loading**: Token → Fetch Entities → Display List
3. **Detail Navigation**: Select Item → Show Complete Info

#### Network Infrastructure
- **Retrofit** for HTTP operations
- **Gson** for JSON serialization
- **OkHttp** for request logging and monitoring
- **Coroutines** for background processing

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

## 🎨 Design Philosophy

- **Material Design 3** implementation
- **Modern, clean interface**
- **Adaptive layouts**
- **Smooth loading animations**
- **Intuitive error messaging**
- **Cohesive visual design system**

## 🛠️ Development Setup

### System Requirements
- Android Studio Flamingo or newer
- Android SDK API 30+
- Kotlin 1.8.0+

### Key Dependencies
```gradle
implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx'
implementation 'androidx.lifecycle:lifecycle-livedata-ktx'
implementation 'com.google.dagger:hilt-android'
implementation 'com.squareup.retrofit2:retrofit'
implementation 'com.squareup.retrofit2:converter-gson'
implementation 'androidx.recyclerview:recyclerview'
implementation 'com.google.android.material:material'
```

### Build Process
1. Import project into Android Studio
2. Sync Gradle dependencies
3. Configure build variants
4. Deploy to device or emulator

## ⚙️ Configuration Management

The system automatically configures based on:
- **Dynamic API responses** for content structure
- **Location-based endpoint** selection
- **Runtime property** mapping
- **Adaptive UI component** rendering

## 🌟 Key Innovations

- **100% Dynamic Architecture**: Zero hardcoded content dependencies
- **Universal Data Adapter**: Works with any API response format
- **Modern Development Stack**: Industry-standard tools and patterns
- **Robust Error Handling**: Comprehensive failure management
- **Intuitive User Experience**: Clean, responsive interface
- **Maintainable Codebase**: Easy to extend and modify

## 📝 Technical Notes

This project showcases:
- Industry-standard Android development practices
- Clean architecture implementation principles
- Modern UI/UX design methodologies
- Comprehensive error handling strategies
- Efficient network communication protocols
- Flexible content management systems

---

## 👨‍💻 Project Information

**Developer**: Siddhant Singh Karki  
**Student ID**: s8091542  
**Course**: NIT3213 - Android Application Development  
**Project**: Final Assignment - Dynamic Mobile Application