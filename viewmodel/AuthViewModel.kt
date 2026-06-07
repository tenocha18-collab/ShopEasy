package com.shopeasy.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.shopeasy.app.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class AuthState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val error: String? = null,
    val userId: String = "",
    val userEmail: String = ""
)

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = AuthRepository()

    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state

    init {
        checkLoginState()
    }

    private fun checkLoginState() {
        if (repo.isLoggedIn()) {
            _state.value = AuthState(
                isLoggedIn = true,
                userId = repo.getUserId(),
                userEmail = repo.currentUser?.email ?: ""
            )
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            val result = repo.login(email, password)
            result.onSuccess {
                _state.value = AuthState(
                    isLoggedIn = true,
                    userId = repo.getUserId(),
                    userEmail = it.email ?: ""
                )
            }
            result.onFailure {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = it.message ?: "Ошибка авторизации"
                )
            }
        }
    }

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            val result = repo.register(email, password)
            result.onSuccess {
                _state.value = AuthState(
                    isLoggedIn = true,
                    userId = repo.getUserId(),
                    userEmail = it.email ?: ""
                )
            }
            result.onFailure {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = it.message ?: "Ошибка регистрации"
                )
            }
        }
    }

    fun logout() {
        repo.logout()
        _state.value = AuthState()
    }

    fun getUserId(): String = repo.getUserId()
}