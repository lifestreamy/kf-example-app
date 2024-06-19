package com.example.app.external_intents

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding
import com.example.app.R
import com.example.app.databinding.ActivityWaitingCardBinding
import com.example.app.prefernces.AppPrefs
import com.example.app.ui.common.BaseActivity
import com.example.app.utils.Globals.appPreferences
import com.example.app.utils.Globals.initSDK
import com.example.app.utils.launchSafe
import com.jakewharton.threetenabp.AndroidThreeTen


class IntentActivity : BaseActivity() {
    private var purchaseInitInvoice = ""

    private lateinit var binding: ViewBinding
    private lateinit var btnCancel: Button

    private fun changeInfoLayoutVisibility(isVisible: Boolean) {
        (binding as ActivityWaitingCardBinding).llInfo.isVisible = isVisible
    }

    private fun changePurchaseLayoutVisibility(isVisible: Boolean) {
        (binding as ActivityWaitingCardBinding).rlBasicPurchase.isVisible = isVisible
    }

    private fun getBtnCancel(): Button {
        return (binding as ActivityWaitingCardBinding).btnCancel
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initSDK(this)
        appPreferences = AppPrefs(this)
        purchaseInitInvoice = ""

        val urlIntent: Intent = intent
        val action = urlIntent.action
        if (action != Intent.ACTION_VIEW) return

        AndroidThreeTen.init(this)

        val actionType = intent.getStringExtra("action")

        if (actionType == null) {
            returnError("не указано actionType:String !!!", "-")
            return
        }

        binding = ActivityWaitingCardBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        btnCancel = getBtnCancel()
        btnCancel.setOnClickListener {
            returnError("Отмена операции", "-")
        }

        changeInfoLayoutVisibility(false)
        changePurchaseLayoutVisibility(true)

        launchSafe("external_intent") {
            when (actionType) {
                "login" -> {
                    val login = intent.getStringExtra("login")
                    val password = intent.getStringExtra("password")

                    if (login == null || password == null) {
                        returnError("должны быть указаны следующие данные: login:String, password:String", "-")
                        return@launchSafe
                    }

                    val intent = Intent()
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }
        }
    }

    private fun returnError(message: String, code: String) {
        val intent = Intent()
        intent.putExtra("message", message)
        intent.putExtra("code", code)
        setResult(Activity.RESULT_CANCELED, intent)
        finish()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        returnError("Отмена операции", "-")
        super.onBackPressed()
    }
}