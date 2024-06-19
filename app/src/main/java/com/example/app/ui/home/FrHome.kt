package com.example.app.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.app.R
import com.example.app.databinding.HomePageBinding
import com.example.app.utils.Globals.currentFragment
import com.example.app.utils.Globals.mainActivity
import com.example.app.utils.Globals.maxAmount
import com.example.app.utils.Globals.overLimit
import com.example.app.utils.Toaster
import com.example.app.utils.asMoney
import com.example.app.utils.launchSafe
import com.example.sdk.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class FrHome : Fragment() {

    private lateinit var binding: HomePageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        retainInstance = true
        currentFragment = this
        binding = HomePageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initKeyboard()

        binding.btnMifareRead.visibility = View.INVISIBLE
        binding.btnMifareCancel.visibility = View.INVISIBLE

        binding.btnPurchase.text = BuildConfig.FLAVOR

        binding.btnMifareCancel.setOnClickListener {
        }

        binding.btnPurchase.setOnClickListener {

        }


        binding.btnCancel.setOnClickListener {
            mainActivity.clearSumPurchaseButton()
        }
    }

    private val DECIMAL_FORMAT = "###,###.##"

    private fun formatValue(value: Number, formatString: String): String? {
        val formatSymbols = DecimalFormatSymbols(Locale.ENGLISH)
        formatSymbols.decimalSeparator = '.'
        formatSymbols.groupingSeparator = ' '
        val formatter = DecimalFormat(formatString, formatSymbols)
        return formatter.format(value)
    }

    private fun initKeyboard() {
        binding.inputLayout.btn0.onClick = { char -> addChar(char) }
        binding.inputLayout.btn1.onClick = { char -> addChar(char) }
        binding.inputLayout.btn2.onClick = { char -> addChar(char) }
        binding.inputLayout.btn3.onClick = { char -> addChar(char) }
        binding.inputLayout.btn4.onClick = { char -> addChar(char) }
        binding.inputLayout.btn5.onClick = { char -> addChar(char) }
        binding.inputLayout.btn6.onClick = { char -> addChar(char) }
        binding.inputLayout.btn7.onClick = { char -> addChar(char) }
        binding.inputLayout.btn8.onClick = { char -> addChar(char) }
        binding.inputLayout.btn9.onClick = { char -> addChar(char) }
        binding.inputLayout.btnPoint.onClick = { char ->
            val newText = binding.etvAmount.text.toString().replace(".", "")
            binding.etvAmount.setText(newText)
            binding.etvAmount.append(char)
        }

        binding.inputLayout.btnDelete.setOnTouchListener { _, e ->
            pressingDelete = e.action == MotionEvent.ACTION_DOWN
            deleteAllChars()
            return@setOnTouchListener false
        }
    }

    private fun addChar(char: String) {
        val amountStr = binding.etvAmount.text.toString()

        if (amountStr.contains(".") &&
            amountStr.length - amountStr.indexOf(".") >= 3
        ) return

        if (char == ".") {
            binding.etvAmount.append(char)
            return
        }

        if (!overLimit(binding.etvAmount, char)) {
            binding.etvAmount.append(char)
            binding.etvAmount.setText(formatValue(binding.etvAmount.text.toString().replace(" ", "").toDouble(), DECIMAL_FORMAT))
        } else Toaster.I()?.show("Максимальная сумма: ${maxAmount.toString().asMoney()}")
    }

    private var pressingDelete = false

    private fun deleteAllChars() {
        launchSafe("fr_home, delete_all_chars") {
            withContext(Dispatchers.Main) {
                while (pressingDelete) {
                    delay(100)
                    if (!inFocus)
                        return@withContext
                    deleteLasChar()
                }
            }
        }
    }

    private fun deleteLasChar() {
        if (binding.etvAmount.text.isNullOrEmpty())
            return

        val preString = binding.etvAmount.text.toString()
        val newString = preString.substring(0, preString.length - 1)
        if (newString.isEmpty()) {
            binding.etvAmount.setText(newString)
        } else {
            binding.etvAmount.setText(formatValue(newString.replace(" ", "").toDouble(), DECIMAL_FORMAT))
        }

    }

    private var inFocus = false

    override fun onResume() {
        super.onResume()
        inFocus = true
        binding.btnPurchase.isEnabled = true
        mainActivity.setMenuItemSelected(R.id.action_payment)
        mainActivity.clearSumPurchaseButton()
    }

    override fun onPause() {
        super.onPause()
        inFocus = false
        mainActivity.clearSumPurchaseButton()
    }

    fun showBtnPurchase() {
        binding.btnPurchase.visibility = View.VISIBLE
        binding.btnCancel.visibility = View.GONE
    }

    fun clearSum() {
        binding.etvAmount.setText("")
    }
}