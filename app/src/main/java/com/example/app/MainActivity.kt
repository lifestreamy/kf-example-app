package com.example.app

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.example.app.databinding.ActivityMainBinding
import com.example.app.navigation.BackController
import com.example.app.prefernces.AppPrefs
import com.example.app.ui.common.BaseActivity
import com.example.app.ui.home.FrHome
import com.example.app.ui.login.FrLoginCompose
import com.example.app.utils.Globals.appPreferences
import com.example.app.utils.Globals.exampleSdk
import com.example.app.utils.Globals.frHome
import com.example.app.utils.Globals.frLoginCompose
import com.example.app.utils.Globals.initSDK
import com.example.app.utils.Globals.isCurrent
import com.example.app.utils.Globals.mainActivity
import com.example.app.utils.Globals.sdk
import com.example.app.utils.Toaster
import com.example.app.utils.launchSafe
import com.example.sdk.sdk.core.ExampleListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Toaster.initInstance(this)
        initSDK(this)

        mainActivity = this
        appPreferences = AppPrefs(this)
        frHome = FrHome()
        frLoginCompose = FrLoginCompose()

        sdk.setListener(object : ExampleListener {
            override fun onExample() {
                Toaster.I()?.show("example")
            }
        })

        binding.menu.post(Runnable {
            binding.menu.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.action_payment -> {
                        mainActivity.clearSumPurchaseButton()
                        mainActivity.setFragment(frHome)
                        return@setOnItemSelectedListener true
                    }
                    R.id.action_schedule -> {
                        mainActivity.clearSumPurchaseButton()
                        return@setOnItemSelectedListener true
                    }
                    R.id.action_history -> {
                        mainActivity.clearSumPurchaseButton()
                        return@setOnItemSelectedListener true
                    }
                    R.id.action_settings -> {
                        mainActivity.clearSumPurchaseButton()
                        return@setOnItemSelectedListener true
                    }
                    else -> return@setOnItemSelectedListener false
                }
            }
        })

        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
        val pInfo = packageManager.getPackageInfo(packageName, 0)
        val version = pInfo.versionName
        val buildType = if (BuildConfig.DEBUG) "DEBUG" else "Release"

        binding.tvTitle.text = "Example\nv.$version $buildType"


        launchSafe("loading_app") {
            withContext(Dispatchers.Main) {
                binding.tvTitle.visibility = View.VISIBLE
                delay(1000)
                binding.tvTitle.visibility = View.GONE
            }
            getAuthState()
        }

    }

    private fun getAuthState() {
        val isAuthorized = exampleSdk.isAuthorized()
        openInitPage(isAuthorized)
    }

    private fun openInitPage(isAuthorized: Boolean) {
        if (isAuthorized) {
            setFragment(frHome)
            binding.menu.visibility = View.VISIBLE
            binding.menu.selectedItemId = R.id.action_payment
        } else {
            binding.menu.visibility = View.GONE
            setFragment(frLoginCompose)
        }
    }

    fun setMenuItemSelected(itemId: Int) {
        binding.menu.selectedItemId = itemId
    }

    override fun onResume() {
        super.onResume()
        exampleSdk.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        clearSumPurchaseButton()
        exampleSdk.onPause(this)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                BackController.onBackPress()
            }
        }
        return false
    }

    fun clearSumPurchaseButton() {
        launchSafe("clear_sum_purchase_button") {
            delay(500)
            if (frHome.isCurrent()) {
                frHome.clearSum()
                frHome.showBtnPurchase()
            }
        }
    }

    override fun onDestroy() {
        exampleSdk.onDestroy()
        super.onDestroy()
    }
}