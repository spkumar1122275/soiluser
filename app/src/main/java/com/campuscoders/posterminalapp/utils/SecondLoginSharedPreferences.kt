package com.campuscoders.posterminalapp.utils

import android.content.Context
import com.campuscoders.posterminalapp.domain.model.AnylocalUser
import com.google.gson.Gson

class SecondLoginSharedPreferences(context: Context) {

    private val prefs = context.getSharedPreferences(
        "second_login_prefs",   // ← Completely isolated
        Context.MODE_PRIVATE
    )

    private val gson = Gson()

    companion object {
        private const val KEY_USER = "second_login_user"
        private const val KEY_ROLE = "second_login_role"
        private const val KEY_LOGIN_TIMESTAMP = "second_login_timestamp"
        private const val KEY_REMEMBER_ME = "second_login_remember_me"
    }

    // -------------------------------------------------------------
    // ✔ Save any user data (Manager or Cashier)
    // -------------------------------------------------------------
    fun saveUser(user: AnylocalUser) {
        prefs.edit().putString(KEY_USER, gson.toJson(user)).apply()
    }

    fun getUser(): AnylocalUser? {
        val json = prefs.getString(KEY_USER, null) ?: return null
        return gson.fromJson(json, AnylocalUser::class.java)
    }

    fun saveSecondLoginUser(
        id: String,
        fullName: String,
        role: String,
        terminalId: String,
        storeId: String,
        taxId: String,
        password: String
    ) {
        prefs.edit().apply {
            putString("id", id)
            putString("fullName", fullName)
            putString("role", role)
            putString("terminalId", terminalId)
            putString("storeId", storeId)
            putString("taxId", taxId)
            putString("password", password)
            apply()
        }
    }

    fun getSecondLoginUser(): Map<String, String?> {
        return mapOf(
            "id" to prefs.getString("id", null),
            "fullName" to prefs.getString("fullName", null),
            "role" to prefs.getString("role", null),
            "terminalId" to prefs.getString("terminalId", null),
            "storeId" to prefs.getString("storeId", null),
            "taxId" to prefs.getString("taxId", null),
            "password" to prefs.getString("password", null)
        )
    }


    // -------------------------------------------------------------
    // ✔ Save Role
    // -------------------------------------------------------------
    fun saveUserRole(role: String) {
        prefs.edit().putString(KEY_ROLE, role).apply()
    }

    fun getUserRole(): String? = prefs.getString(KEY_ROLE, null)

    // -------------------------------------------------------------
    // ✔ Login timestamp (for session expiration)
    // -------------------------------------------------------------
    fun saveLoginTimestamp() {
        prefs.edit().putLong(KEY_LOGIN_TIMESTAMP, System.currentTimeMillis()).apply()
    }

    fun getLoginTimestamp(): Long = prefs.getLong(KEY_LOGIN_TIMESTAMP, 0L)

    // -------------------------------------------------------------
    // ✔ Remember Me
    // -------------------------------------------------------------
    fun setRememberMe(isRemember: Boolean) {
        prefs.edit().putBoolean(KEY_REMEMBER_ME, isRemember).apply()
    }

    fun isRememberMe(): Boolean = prefs.getBoolean(KEY_REMEMBER_ME, false)

    // -------------------------------------------------------------
    // ✔ Clear second login data
    // -------------------------------------------------------------
    fun clear() {
        prefs.edit().clear().apply()
    }
}
