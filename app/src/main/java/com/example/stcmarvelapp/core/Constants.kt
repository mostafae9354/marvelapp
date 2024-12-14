package com.example.stcmarvelapp.core

import com.example.stcmarvelapp.BuildConfig
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

class Constants {

    companion object {
        const val BASE_URL = "https://gateway.marvel.com"
        const val API_KEY = BuildConfig.MY_API_KEY
        private const val PRIVATE_KEY = BuildConfig.MY_SECRET_KEY
        val timestamp = Timestamp(System.currentTimeMillis()).time.toString()
        const val LIMIT = 20

        fun hash(): String {
            val input = "$timestamp$PRIVATE_KEY$API_KEY"
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
        }
    }
}