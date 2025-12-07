package com.campuscoders.posterminalapp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.campuscoders.posterminalapp.di.SecurityUtils
import com.campuscoders.posterminalapp.domain.model.AnylocalUser
import com.campuscoders.posterminalapp.domain.repository.SecondAuthRepository
import com.campuscoders.posterminalapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginTwoViewModel @Inject constructor(
    private val authRepository: SecondAuthRepository
) : ViewModel() {

    private val _loginState = MutableLiveData<Resource<AnylocalUser>>()
    val loginState: LiveData<Resource<AnylocalUser>> get() = _loginState

    /**
     * Offline login (TerminalUser + MainUser locally)
     */
    fun loginOffline(terminalId: String, password: String) {
        _loginState.value = Resource.Loading(null)

        viewModelScope.launch {
            val hashedPassword = SecurityUtils.hashPasswordSHA256(password)

            val user = authRepository.loginOffline(terminalId, hashedPassword)

            if (user == null) {
                _loginState.value = Resource.Error(null, "User not found")
            } else {
                authRepository.saveSession(user)
                _loginState.value = Resource.Success(user)
            }
        }
    }


    /** Reset state (for UI to clear error/messages) */
    fun resetLoginState() {
        _loginState.value = Resource.Idle()
    }
}
