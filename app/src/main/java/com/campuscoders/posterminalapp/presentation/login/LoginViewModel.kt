package com.campuscoders.posterminalapp.presentation.login

import android.content.Context
import androidx.lifecycle.*
import com.campuscoders.posterminalapp.R
import com.campuscoders.posterminalapp.di.SecurityUtils
import com.campuscoders.posterminalapp.domain.model.LoginParams
import com.campuscoders.posterminalapp.domain.model.LoginResult
import com.campuscoders.posterminalapp.domain.use_case.login.HandleLoginUseCase
import com.campuscoders.posterminalapp.utils.CustomSharedPreferences
import com.campuscoders.posterminalapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val handleLoginUseCase: HandleLoginUseCase,
    private val prefs: CustomSharedPreferences,
    @ApplicationContext private val appContext: Context
) : ViewModel() {

    private val _loginStatus = MutableLiveData<Resource<LoginResult>>()
    val loginStatus: LiveData<Resource<LoginResult>> get() = _loginStatus

    private val _rememberMeChecked = MutableLiveData<Boolean>()
    val rememberMeChecked: LiveData<Boolean> get() = _rememberMeChecked

    private val _savedLoginFields = MutableLiveData<Map<String, String>>()
    val savedLoginFields: LiveData<Map<String, String>> get() = _savedLoginFields

    init {
        loadRememberMeState()
    }

    private fun loadRememberMeState() {
        val isRemembered = prefs.getControl()
        _rememberMeChecked.value = isRemembered

        if (isRemembered) {
            val saved = prefs.getMainUserLogin(appContext).mapNotNullValues()
            _savedLoginFields.value = saved
        }
    }

    fun handleLogin(terminalId: String, taxId: String, storeId: String, password: String) {
        _loginStatus.value = Resource.Loading(null)

        viewModelScope.launch {
            val hashedPassword = SecurityUtils.hashPasswordSHA256(password)

            val loginParams = LoginParams(
                terminalId = terminalId,
                taxId = taxId,
                storeId = storeId,
                password = hashedPassword
            )

            val result = handleLoginUseCase(loginParams)
            _loginStatus.postValue(result)

            // ✅ Save credentials AFTER successful login if "Remember Me" is on
            if (result is Resource.Success && _rememberMeChecked.value == true) {
                prefs.setMainUserLogin(
                    terminalId = terminalId,
                    taxId = taxId,
                    memberStoreId = storeId,
                    password = password, // Save the plain text password
                    context = appContext
                )
            }
        }
    }


    fun updateRememberMeState(isChecked: Boolean) {
        prefs.setControl(isChecked)
        _rememberMeChecked.value = isChecked

        // ✅ If "Remember Me" is turned off, clear the saved credentials
        if (!isChecked) {
            prefs.setMainUserLogin("", "", "", "", appContext)
        }
    }
}

// ✅ helper extension to clean nulls
private fun HashMap<String, Any>.mapNotNullValues(): Map<String, String> = 
    mapNotNull { (k, v) -> 
        (v as? String)?.takeIf { it != "null" }?.let { k to it }
    }.toMap()