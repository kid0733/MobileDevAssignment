package com.example.nit3213app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for Hilt dependency injection
 * 
 * @HiltAndroidApp annotation triggers Hilt's code generation including a base class
 * for your application that serves as the application-level dependency container.
 */

 /**
  * FILE 1
  * This is my app's foundation. The @HiltAndroidApp annotation tells Hilt to set up dependency injection for the entire application. This runs before any screens appear and enables me to inject dependencies anywhere in the app.
  */
@HiltAndroidApp
class NIT3213Application : Application() 

// Next we'll look at NetworkModule.kt 