package com.campuscoders.posterminalapp.utils

import androidx.room.TypeConverter
import com.campuscoders.posterminalapp.domain.model.TerminalUsers
import com.campuscoders.posterminalapp.domain.model.MainUser
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromMainUserList(mainUsers: List<MainUser>?): String? {
        return mainUsers?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toMainUserList(mainUsersString: String?): List<MainUser>? {
        return mainUsersString?.let {
            val listType = object : TypeToken<List<MainUser>>() {}.type
            gson.fromJson(it, listType)
        }
    }

    @TypeConverter
    fun fromTerminalUsersList(terminalUsers: List<TerminalUsers>?): String? {
        if (terminalUsers == null) {
            return null        }
        val type = object : TypeToken<List<TerminalUsers>>() {}.type
        return gson.toJson(terminalUsers, type)
    }

    @TypeConverter
    fun toTerminalUsersList(terminalUsersString: String?): List<TerminalUsers>? {
        if (terminalUsersString == null) {
            return null
        }
        val type = object : TypeToken<List<TerminalUsers>>() {}.type
        return gson.fromJson(terminalUsersString, type)
    }


}
