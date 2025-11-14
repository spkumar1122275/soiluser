package com.campuscoders.posterminalapp.di

import java.security.MessageDigest

object SecurityUtils {
    fun hashPasswordSHA256(password: String): String {
        val bytes = password.toByteArray(Charsets.UTF_8)
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.joinToString("") { "%02X".format(it) } // Uppercase hex to match Oracle
    }
}