package com.hugh.outsourcing.bank_acs

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import com.hugh.outsourcing.bank_acs.databinding.ActivityAuthBinding
import java.util.*

class AuthActivity : BaseActivity() {
    private lateinit var binding: ActivityAuthBinding
    private var year = 2022
    private var month = 3
    private var day = 8
    companion object{
        private const val id="id"
        fun getResultIntent(year:Int,month:Int,day:Int): Intent {
            return Intent().apply {
                putExtra(id,"$year/$month/$day")
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonsAction()
    }
    private fun buttonsAction(){
        val calendar = Calendar.getInstance()
        binding.datePicker.init(calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)){ _: DatePicker, y:Int, MoY:Int, DoM:Int ->
            year = y
            month = MoY+1
            day = DoM
        }
        binding.confirm.setOnClickListener {
            setResult(Activity.RESULT_OK, getResultIntent(year,month,day))
            finish()
        }
    }
}