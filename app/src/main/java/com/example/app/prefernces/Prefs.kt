package com.example.app.prefernces

interface Prefs {
    var terminalId: String
    var merchantId: String
    var defaultPhone: String
    var printSlipReceipt: Int
    var lastErn: String
    var shopName: String
    var shopAddress: String
    var login: String
    var password: String
    var autoCloseSession: Boolean
    var autocloseTime: String
}