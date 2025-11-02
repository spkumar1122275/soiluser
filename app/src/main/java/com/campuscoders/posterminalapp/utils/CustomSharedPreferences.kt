package com.campuscoders.posterminalapp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.campuscoders.posterminalapp.R
import com.campuscoders.posterminalapp.data.remote.dto.UserData

class CustomSharedPreferences(context: Context) {

    companion object {
        private const val PREFERENCE_MAIN_USER_SAVE = "main_user_save"
        private const val PREFERENCE_MAIN_USER_LOGIN = "main_user_login"
        private const val PREFERENCE_TERMINAL_USER_LOGIN = "terminal_user_login"
        private const val PREFERENCE_LOGIN_RESPONSE = "login_pref"
        private const val NULL = "null"

        @Volatile
        private var instance: CustomSharedPreferences? = null

        operator fun invoke(context: Context): CustomSharedPreferences =
            instance ?: synchronized(this) {
                instance ?: CustomSharedPreferences(context.applicationContext).also {
                    instance = it
                }
            }
    }

    // region === SharedPreferences References ===
    private val sharedMainUser: SharedPreferences =
        context.getSharedPreferences(PREFERENCE_MAIN_USER_SAVE, Context.MODE_PRIVATE)

    private val sharedMainUserLogin: SharedPreferences =
        context.getSharedPreferences(PREFERENCE_MAIN_USER_LOGIN, Context.MODE_PRIVATE)

    private val sharedTerminalUserLogin: SharedPreferences =
        context.getSharedPreferences(PREFERENCE_TERMINAL_USER_LOGIN, Context.MODE_PRIVATE)

    private val sharedLoginResponse: SharedPreferences =
        context.getSharedPreferences(PREFERENCE_LOGIN_RESPONSE, Context.MODE_PRIVATE)
    // endregion

    // region === Login Response (from API) ===
    fun saveUserData(user: UserData) {
        sharedLoginResponse.edit {
            putString("terminal_id", user.terminalId)
            putString("tax_id", user.taxId)
            putString("member_store", user.memberStore)
            putString("role", user.role)
            putString("first_name", user.firstName)
            putString("last_name", user.lastName)
            putString("cellphone_number", user.cellphoneNumber)
            putString("access_token", user.accessToken)
        }
    }

    fun getUserData(): UserData? {
        val terminalId = sharedLoginResponse.getString("terminal_id", null) ?: return null
        return UserData(
            terminalId = terminalId,
            taxId = sharedLoginResponse.getString("tax_id", null),
            memberStore = sharedLoginResponse.getString("member_store", null),
            role = sharedLoginResponse.getString("role", null),
            firstName = sharedLoginResponse.getString("first_name", null),
            lastName = sharedLoginResponse.getString("last_name", null),
            cellphoneNumber = sharedLoginResponse.getString("cellphone_number", null),
            accessToken = sharedLoginResponse.getString("access_token", null)
        )
    }

    fun getAccessToken(): String? = sharedLoginResponse.getString("access_token", null)
    fun getUserRole(): String? = sharedLoginResponse.getString("role", null)
    fun getUserTerminalId(): String? = sharedLoginResponse.getString("terminal_id", null)
    fun getUserFirstName(): String? = sharedLoginResponse.getString("first_name", null)
    fun getUserLastName(): String? = sharedLoginResponse.getString("last_name", null)
    // endregion

    // region === Remember Me + Main User Login ===
    fun setMainUserLogin(
        terminalId: String,
        vknTckn: String,
        memberStoreId: String,
        password: String,
        context: Context
    ) {
        sharedMainUserLogin.edit {
            putString(context.getString(R.string.user_terminal_id), terminalId)
            putString(context.getString(R.string.user_vkn_tckn), vknTckn)
            putString(context.getString(R.string.user_uye_isyeri_no), memberStoreId)
            putString(context.getString(R.string.user_password), password)
        }
    }

    fun setMainUserLoginRememberMeManager(remember: Boolean, context: Context) {
        sharedMainUserLogin.edit {
            putBoolean(context.getString(R.string.user_remember_me_manager), remember)
        }
    }

    fun setMainUserLoginRememberMeCashier(remember: Boolean, context: Context) {
        sharedMainUserLogin.edit {
            putBoolean(context.getString(R.string.user_remember_me_terminal), remember)
        }
    }

    fun getMainUserLogin(context: Context): HashMap<String, Any> {
        val hashMap = hashMapOf<String, Any>()

        hashMap[context.getString(R.string.user_terminal_id)] =
            sharedMainUserLogin.getString(context.getString(R.string.user_terminal_id), NULL) ?: NULL
        hashMap[context.getString(R.string.user_vkn_tckn)] =
            sharedMainUserLogin.getString(context.getString(R.string.user_vkn_tckn), NULL) ?: NULL
        hashMap[context.getString(R.string.user_uye_isyeri_no)] =
            sharedMainUserLogin.getString(context.getString(R.string.user_uye_isyeri_no), NULL) ?: NULL
        hashMap[context.getString(R.string.user_password)] =
            sharedMainUserLogin.getString(context.getString(R.string.user_password), NULL) ?: NULL
        hashMap[context.getString(R.string.user_remember_me_manager)] =
            sharedMainUserLogin.getBoolean(context.getString(R.string.user_remember_me_manager), false)
        hashMap[context.getString(R.string.user_remember_me_terminal)] =
            sharedMainUserLogin.getBoolean(context.getString(R.string.user_remember_me_terminal), false)

        return hashMap
    }
    // endregion

    // region === Terminal User Login ===
    fun setTerminalUserLogin(
        terminalId: String,
        vknTckn: String,
        memberStoreId: String,
        password: String,
        fullName: String,
        date: String,
        time: String,
        iptalIade: Boolean,
        tahsilat: Boolean,
        kasiyerGoruntuleme: Boolean,
        kasiyerEklemeDuzenleme: Boolean,
        kasiyerSilme: Boolean,
        urunGoruntuleme: Boolean,
        urunEklemeDuzenleme: Boolean,
        urunSilme: Boolean,
        tumRaporlariGoruntuleme: Boolean,
        raporKaydetGonder: Boolean,
        posYonetimi: Boolean,
        admin: Boolean,
        context: Context
    ) {
        sharedTerminalUserLogin.edit {
            putString(context.getString(R.string.user_terminal_id), terminalId)
            putString(context.getString(R.string.user_vkn_tckn), vknTckn)
            putString(context.getString(R.string.user_uye_isyeri_no), memberStoreId)
            putString(context.getString(R.string.user_password), password)
            putString(context.getString(R.string.user_full_name), fullName)
            putString(context.getString(R.string.user_date), date)
            putString(context.getString(R.string.user_time), time)
            putBoolean(context.getString(R.string.user_iptal_iade), iptalIade)
            putBoolean(context.getString(R.string.user_tahsilat), tahsilat)
            putBoolean(context.getString(R.string.user_kasiyer_goruntuleme), kasiyerGoruntuleme)
            putBoolean(context.getString(R.string.user_kasiyer_ekleme_duzenleme), kasiyerEklemeDuzenleme)
            putBoolean(context.getString(R.string.user_kasiyer_silme), kasiyerSilme)
            putBoolean(context.getString(R.string.user_urun_goruntuleme), urunGoruntuleme)
            putBoolean(context.getString(R.string.user_urun_ekleme_duzenleme), urunEklemeDuzenleme)
            putBoolean(context.getString(R.string.user_urun_silme), urunSilme)
            putBoolean(context.getString(R.string.user_tum_raporları_goruntuleme), tumRaporlariGoruntuleme)
            putBoolean(context.getString(R.string.user_rapor_kaydet_gonder), raporKaydetGonder)
            putBoolean(context.getString(R.string.user_pos_yonetimi), posYonetimi)
            putBoolean(context.getString(R.string.user_admin), admin)
        }
    }

    fun getTerminalUserLogin(context: Context): HashMap<String, Any> {
        val hashMap = hashMapOf<String, Any>()
        hashMap[context.getString(R.string.user_terminal_id)] =
            sharedTerminalUserLogin.getString(context.getString(R.string.user_terminal_id), NULL) ?: NULL
        hashMap[context.getString(R.string.user_vkn_tckn)] =
            sharedTerminalUserLogin.getString(context.getString(R.string.user_vkn_tckn), NULL) ?: NULL
        hashMap[context.getString(R.string.user_uye_isyeri_no)] =
            sharedTerminalUserLogin.getString(context.getString(R.string.user_uye_isyeri_no), NULL) ?: NULL
        hashMap[context.getString(R.string.user_password)] =
            sharedTerminalUserLogin.getString(context.getString(R.string.user_password), NULL) ?: NULL
        return hashMap
    }
    // endregion

    // region === Generic Remember Me Flag ===
    fun setControl(control: Boolean) {
        sharedMainUser.edit {
            putBoolean("control", control)
        }
    }

    fun getControl(): Boolean = sharedMainUser.getBoolean("control", false)
    // endregion

    // region === Timestamp / Expiry ===
    fun saveLoginTimestamp() {
        sharedLoginResponse.edit {
            putLong("last_login_time", System.currentTimeMillis())
        }
    }

    fun getLastLoginTimestamp(): Long =
        sharedLoginResponse.getLong("last_login_time", 0L)

    fun isLoginExpired(maxHours: Int = 24): Boolean {
        val lastLogin = getLastLoginTimestamp()
        if (lastLogin == 0L) return true
        val elapsed = System.currentTimeMillis() - lastLogin
        return elapsed > maxHours * 60 * 60 * 1000
    }
    // endregion

    // region === Session Clear ===
    fun clearUserData() {
        sharedMainUser.edit().clear().apply()
        sharedMainUserLogin.edit().clear().apply()
        sharedTerminalUserLogin.edit().clear().apply()
        sharedLoginResponse.edit().clear().apply()
    }
    // endregion
}
