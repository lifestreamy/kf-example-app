package com.example.app.prefernces

import android.content.Context
import com.example.app.prefernces.Constants.FILE_AUTO_CLOSE_SESSION
import com.example.app.prefernces.Constants.FILE_AUTO_CLOSE_TIME
import com.example.app.prefernces.Constants.FILE_DEFAULT_PHONE
import com.example.app.prefernces.Constants.FILE_LAST_ERN
import com.example.app.prefernces.Constants.FILE_LOGIN
import com.example.app.prefernces.Constants.FILE_MERCHANT_ID
import com.example.app.prefernces.Constants.FILE_PASSWORD
import com.example.app.prefernces.Constants.FILE_PRINT_SLIP
import com.example.app.prefernces.Constants.FILE_SESSION_DATE
import com.example.app.prefernces.Constants.FILE_SHOP_ADDRESS
import com.example.app.prefernces.Constants.FILE_SHOP_NAME
import com.example.app.prefernces.Constants.FILE_TERMINAL_ID

class AppPrefs(val context: Context) : Prefs {

    private val prefsManager = PrefsManager(context)

    override var autoCloseSession: Boolean
        get() = prefsManager.readBool(FILE_AUTO_CLOSE_SESSION)
        set(value) = prefsManager.writeBool(value, FILE_AUTO_CLOSE_SESSION)

    override var terminalId: String
        get() = prefsManager.readString(FILE_TERMINAL_ID)
        set(terminalId) = prefsManager.writeString(terminalId, FILE_TERMINAL_ID)

    override var merchantId: String
        get() = prefsManager.readString(FILE_MERCHANT_ID)
        set(merchantId) = prefsManager.writeString(merchantId, FILE_MERCHANT_ID)

    override var defaultPhone: String
        get() = prefsManager.readString(FILE_DEFAULT_PHONE)
        set(defaultPhone) = prefsManager.writeString(defaultPhone, FILE_DEFAULT_PHONE)

    override var printSlipReceipt: Int
        get() = prefsManager.readInt(FILE_PRINT_SLIP, 0)
        set(printSlipMode) = prefsManager.writeInt(printSlipMode, FILE_PRINT_SLIP)

    override var lastErn: String
        get() {
            val lastERN = prefsManager.readInt(FILE_LAST_ERN, 5000)
            val newERN = lastERN + 1
            prefsManager.writeInt(newERN, FILE_LAST_ERN)
            return newERN.toString()
        }
        set(ern) = prefsManager.writeInt(ern.toInt(), FILE_LAST_ERN)

    override var shopName: String
        get() = prefsManager.readString(FILE_SHOP_NAME)
        set(name) = prefsManager.writeString(name, FILE_SHOP_NAME)

    override var shopAddress: String
        get() = prefsManager.readString(FILE_SHOP_ADDRESS)
        set(address) = prefsManager.writeString(address, FILE_SHOP_ADDRESS)

    override var login: String
        get() = prefsManager.readString(FILE_LOGIN)
        set(name) = prefsManager.writeString(name, FILE_LOGIN)

    override var password: String
        get() = prefsManager.readString(FILE_PASSWORD)
        set(address) = prefsManager.writeString(address, FILE_PASSWORD)

    internal var sessionDate: String
        get() = prefsManager.readStringDate(FILE_SESSION_DATE)
        set(sessionDate) = prefsManager.writeString(sessionDate, FILE_SESSION_DATE)

    override var autocloseTime: String
        get() = prefsManager.readString(FILE_AUTO_CLOSE_TIME, "23:59")
        set(autocloseTime) = prefsManager.writeString(autocloseTime, FILE_AUTO_CLOSE_TIME)
}
