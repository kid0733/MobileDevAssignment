package com.example.nit3213app

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.nit3213app.data.model.LoginResponse
import com.example.nit3213app.repository.MainRepository
import com.example.nit3213app.viewmodel.LoginResult
import com.example.nit3213app.viewmodel.LoginViewModel
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

/**
 * Unit tests for LoginViewModel
 * 
 * Tests the business logic for user authentication
 * using MockK to mock repository dependencies.
 */
@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var repository: MainRepository
    private lateinit var viewModel: LoginViewModel
    private lateinit var loginResultObserver: Observer<LoginResult?>
    private lateinit var isLoadingObserver: Observer<Boolean>

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = LoginViewModel(repository)
        loginResultObserver = mockk(relaxed = true)
        isLoadingObserver = mockk(relaxed = true)
        
        viewModel.loginResult.observeForever(loginResultObserver)
        viewModel.isLoading.observeForever(isLoadingObserver)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        viewModel.loginResult.removeObserver(loginResultObserver)
        viewModel.isLoading.removeObserver(isLoadingObserver)
    }

    @Test
    fun `login with valid credentials should return success`() {
        // Arrange
        val username = "Siddhant"
        val password = "s8091542"
        val keypass = "test_keypass_123"
        val loginResponse = LoginResponse(keypass)
        val response = Response.success(loginResponse)
        
        coEvery { repository.login(username, password) } returns response
        
        // Act
        viewModel.login(username, password)
        
        // Assert
        verify { isLoadingObserver.onChanged(true) }
        verify { isLoadingObserver.onChanged(false) }
        verify { loginResultObserver.onChanged(LoginResult.Success(keypass)) }
    }

    @Test
    fun `login with empty username should return error`() {
        // Arrange
        val username = ""
        val password = "s8091542"
        
        // Act
        viewModel.login(username, password)
        
        // Assert
        verify { loginResultObserver.onChanged(LoginResult.Error("Username and password cannot be empty")) }
        verify(exactly = 0) { isLoadingObserver.onChanged(true) }
    }

    @Test
    fun `login with empty password should return error`() {
        // Arrange
        val username = "Siddhant"
        val password = ""
        
        // Act
        viewModel.login(username, password)
        
        // Assert
        verify { loginResultObserver.onChanged(LoginResult.Error("Username and password cannot be empty")) }
        verify(exactly = 0) { isLoadingObserver.onChanged(true) }
    }

    @Test
    fun `login with blank credentials should return error`() {
        // Arrange
        val username = "   "
        val password = "   "
        
        // Act
        viewModel.login(username, password)
        
        // Assert
        verify { loginResultObserver.onChanged(LoginResult.Error("Username and password cannot be empty")) }
    }

    @Test
    fun `login with unsuccessful response should return error`() {
        // Arrange
        val username = "Siddhant"
        val password = "wrong_password"
        val response = Response.error<LoginResponse>(401, mockk(relaxed = true))
        
        coEvery { repository.login(username, password) } returns response
        
        // Act
        viewModel.login(username, password)
        
        // Assert
        verify { isLoadingObserver.onChanged(true) }
        verify { isLoadingObserver.onChanged(false) }
        verify { loginResultObserver.onChanged(match<LoginResult.Error> { 
            it.message.startsWith("Login failed:") 
        }) }
    }

    @Test
    fun `login with network exception should return error`() {
        // Arrange
        val username = "Siddhant"
        val password = "s8091542"
        val exception = Exception("Network error")
        
        coEvery { repository.login(username, password) } throws exception
        
        // Act
        viewModel.login(username, password)
        
        // Assert
        verify { isLoadingObserver.onChanged(true) }
        verify { isLoadingObserver.onChanged(false) }
        verify { loginResultObserver.onChanged(LoginResult.Error("Network error: Network error")) }
    }

    @Test
    fun `clearLoginResult should set result to null`() {
        // Act
        viewModel.clearLoginResult()
        
        // Assert
        verify { loginResultObserver.onChanged(null) }
    }
} 