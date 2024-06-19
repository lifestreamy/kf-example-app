package com.example.app.navigation

import androidx.appcompat.app.AppCompatActivity
import com.example.app.utils.Globals.frHome
import com.example.app.utils.Globals.frLoginCompose
import com.example.app.utils.Globals.isCurrent
import com.example.app.utils.Globals.mainActivity
import com.example.app.utils.Toaster

object BackController {

    fun onBackPress() {
        if (frHome.isCurrent() || frLoginCompose.isCurrent())
            exit(mainActivity)
        else
            mainActivity.supportFragmentManager.popBackStack()
    }

    private var previousPress: Long = 0//для обработки двойного клика (выход из приложения)

    private fun pauseBetweenPressing(): Long {
        val result: Long
        val currentPress = System.currentTimeMillis()
        result = currentPress - previousPress
        previousPress = currentPress
        return result
    }

    private fun exit(activity: AppCompatActivity) {
        if (pauseBetweenPressing() < 3000) {
            activity.finish()
        } else {
           Toaster.I()?.show("Нажмите еще раз для выхода из приложения")
        }
    }
}