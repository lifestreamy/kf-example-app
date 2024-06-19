package com.example.app.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.app.MainActivity
import com.example.app.prefernces.AppPrefs
import com.example.app.ui.home.FrHome
import com.example.app.ui.login.FrLoginCompose
import com.example.sdk.sdk.core.IExampleSdk
import com.example.sdk.sdk.core.ISDK
import com.example.sdk.sdk.core.Sdk
import timber.log.Timber


internal object Globals {
    lateinit var imm: InputMethodManager
    lateinit var appContext: Context
    lateinit var currentFragment: Fragment
    lateinit var mainActivity: MainActivity
    lateinit var intentActivity: AppCompatActivity
    lateinit var frLoginCompose: FrLoginCompose
    lateinit var frHome: FrHome
    lateinit var sdk: IExampleSdk
    lateinit var exampleSdk: ISDK
    lateinit var appPreferences: AppPrefs

    fun isMainInitialized(): Boolean {
        return this::mainActivity.isInitialized
    }
    fun isIntentInitialized(): Boolean {
        return this::intentActivity.isInitialized
    }

    fun initSDK(context: Context) {
        sdk = Sdk.getInstance(context)
        exampleSdk = sdk.sdk
    }

    fun Fragment.isCurrent(): Boolean =
        Globals::currentFragment.isInitialized &&
                this::class.java == currentFragment.javaClass &&
                this == currentFragment


    const val maxAmount = 9999999.99
    fun overLimit(editText: EditText, char: String): Boolean {
        return try {
            val currentAmount = (editText.text.toString() + char).toDouble()
            (currentAmount > maxAmount)
        } catch (e: Exception) {
            Timber.e("ошибка при вводе цифр")
            false
        }
    }
}