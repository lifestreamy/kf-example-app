package com.example.app.prefernces

import android.content.Context
import android.content.SharedPreferences
import org.threeten.bp.LocalDateTime

class PrefsManager(val context: Context) {

    private val sharedPref: SharedPreferences = context.getSharedPreferences("preferences", Context.MODE_PRIVATE)
    private val editor = sharedPref.edit()

    private val defaultStringValue = ""
    private val defaultDateValue = LocalDateTime.now().toString()

    fun writeString(notice: String, fileName: String) {
        editor.putString(fileName, notice)
        editor.apply()
    }

    fun readString(fileName: String, defaultValue: String? = defaultStringValue): String {
        return sharedPref.getString(fileName, defaultValue)!!
    }

    fun readStringDate(fileName: String): String {
        return sharedPref.getString(fileName, defaultDateValue)!!
    }

    fun writeInt(value: Int, fileName: String) {
        editor.putInt(fileName, value)
        editor.apply()
    }

    fun readInt(fileName: String, defValue: Int = -1): Int {
        return sharedPref.getInt(fileName, defValue)
    }

    fun writeBool(value: Boolean, fileName: String) {
        editor.putBoolean(fileName, value)
        editor.apply()
    }

    fun readBool(fileName: String): Boolean {
        return sharedPref.getBoolean(fileName, false)
    }
}