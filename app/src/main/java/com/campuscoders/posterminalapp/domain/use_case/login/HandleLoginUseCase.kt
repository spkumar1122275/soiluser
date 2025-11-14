package com.campuscoders.posterminalapp.domain.use_case.login


import android.content.Context
import com.campuscoders.posterminalapp.data.remote.dto.UserData
import com.campuscoders.posterminalapp.di.SecurityUtils
import com.campuscoders.posterminalapp.domain.model.MainUser
import com.campuscoders.posterminalapp.domain.model.TerminalUsers
import com.campuscoders.posterminalapp.utils.*
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class HandleLoginUseCase @Inject constructor(
    private val loginWithApiUseCase: LoginWithApiUseCase,
    private val fetchMainUserUseCase: FetchMainUserUseCase,
    private val saveMainUserUseCase: SaveMainUserUseCase,
    private val saveTerminalUserUseCase: SaveTerminalUserUseCase,
    private val fetchAndSaveTerminalUsersUseCase: FetchAndSaveTerminalUsersUseCase,
    private val prefs: CustomSharedPreferences,
    @ApplicationContext private val appContext: Context
) {

    suspend operator fun invoke(mainUser: MainUser): Resource<LoginResult> {
        return try {
            // 🧩 STEP 1: Check locally stored session
            val localTerminalId = prefs.getUserTerminalId()
            val localAccessToken = prefs.getAccessToken()
            val localRole = prefs.getUserRole()
            val isExpired = prefs.isLoginExpired(24) // ✅ 24 hours validity window

            if (!localAccessToken.isNullOrEmpty() &&
                localTerminalId == mainUser.mainUserTerminalId &&
                !isExpired
            ) {
                // ✅ Token is still valid → allow offline login
                return Resource.Success(
                    LoginResult(
                        role = localRole ?: Roles.MAIN_USER,
                        userData = prefs.getUserData(),
                        isOffline = true
                    )
                )
            }

            // 🧩 STEP 2: Either token missing or expired → online login
            when (val response = loginWithApiUseCase.executeLoginWithApi(
                terminalId = mainUser.mainUserTerminalId.orEmpty(),
                taxId = mainUser.mainUserTaxId.orEmpty(),
                memberId = mainUser.mainUserStoreId.orEmpty(),
                password = SecurityUtils.hashPasswordSHA256(mainUser.mainUserPassword.orEmpty())
            )) {
                is Resource.Success -> {
                    val userData = response.data?.user
                    if (userData != null) {
                        prefs.saveLoginTimestamp() // ✅ Save new timestamp
                        handleOnlineLoginSuccess(mainUser, userData)
                    } else {
                        Resource.Error(null, "Invalid server response")
                    }
                }

                is Resource.Error -> {
                    // 🧩 STEP 3: API login failed → try offline fallback
                    if (!isExpired && !localAccessToken.isNullOrEmpty()) {
                        // ✅ Token valid but maybe network issue → reuse
                        Resource.Success(
                            LoginResult(
                                role = localRole ?: Roles.MAIN_USER,
                                userData = prefs.getUserData(),
                                isOffline = true
                            )
                        )
                    } else {
                        handleOfflineLogin(mainUser)
                    }
                }

                is Resource.Loading -> Resource.Loading(null)
            }
        } catch (e: Exception) {
            // 🧩 STEP 4: Unexpected error → offline fallback
            handleOfflineLogin(mainUser)
        }
    }


    private suspend fun handleOnlineLoginSuccess(
        mainUser: MainUser,
        userData: UserData
    ): Resource<LoginResult> {
        val role = userData.role?.lowercase() ?: Roles.MAIN_USER

        prefs.saveUserData(userData)
        prefs.saveLoginTimestamp() // ✅ Update timestamp

        prefs.setMainUserLogin(
            mainUser.mainUserTerminalId.orEmpty(),
            mainUser.mainUserTaxId.orEmpty(),
            mainUser.mainUserStoreId.orEmpty(),
            mainUser.mainUserPassword.orEmpty(),
            appContext
        )

        if (role == Roles.MAIN_USER) {
            saveMainUserUseCase.executeSaveMainUser(mainUser)
        } else if (role == Roles.TERMINAL_USER) {
            val fetchResult = fetchAndSaveTerminalUsersUseCase(
                userData.accessToken.orEmpty(),
                userData.terminalId.orEmpty()
            )
            if (fetchResult is Resource.Error) {
                return Resource.Error(null, "Failed to fetch terminal users")
            }

            saveTerminalUserUseCase.executeSaveTerminalUser(
                TerminalUsers(
                    terminalUserTerminalId = userData.terminalId,
                    terminalUsertaxId = userData.taxId,
                    terminalUserUyeIsyeriNo = userData.memberStore,
                    terminalUserFullName = "${userData.firstName} ${userData.lastName}",
                    terminalUserPassword = mainUser.mainUserPassword,
                    terminalUserDate = TimeAndDate.getLocalDate(Constants.DATE_FORMAT),
                    terminalUserTime = TimeAndDate.getTime(),
                    terminalUserIptalIade = Constants.IPTAL_IADE,
                    terminalUserTahsilat = Constants.TAHSILAT,
                    terminalUserKasiyerGoruntuleme = Constants.KASIYER_GORUTULEME,
                    terminalUserKasiyerEklemeDuzenleme = Constants.KASIYER_EKLEME_DUZENLEME,
                    terminalUserKasiyerSilme = Constants.KASIYER_SILME,
                    terminalUserUrunGoruntuleme = Constants.URUN_GORUNTULEME,
                    terminalUserUrunEklemeDuzenleme = Constants.URUN_EKLEME_DUZENLEME,
                    terminalUserUrunSilme = Constants.URUN_SILME,
                    terminalUserTumRaporlariGoruntule = Constants.TUM_RAPORLARI_GORUNTULEME,
                    terminalUserRaporKaydetGonder = Constants.RAPOR_KAYDET_GONDER,
                    terminalUserPosYonetimi = Constants.POS_YONETIMI,
                    terminalUserAdmin = Constants.ADMIN
                )
            )
        }

        return Resource.Success(
            LoginResult(role = role, userData = userData, isOffline = false)
        )
    }




    private fun handleOfflineLogin(mainUser: MainUser): Resource<LoginResult> {
        val localUserData = prefs.getUserData()
        val localRole = prefs.getUserRole()

        return if (localUserData != null &&
            localUserData.terminalId == mainUser.mainUserTerminalId &&
            !prefs.isLoginExpired(24)
        ) {
            Resource.Success(
                LoginResult(
                    role = localRole ?: Roles.MAIN_USER,
                    userData = localUserData,
                    isOffline = true
                )
            )
        } else {
            Resource.Error(null, "Offline login not available or expired session")
        }
    }

}
