package com.hugh.outsourcing.bank_acs

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity:AppCompatActivity() {
    companion object{
        var activityCount = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ++activityCount
        L.e("BaseActivity","Create a new activity ${this.componentName.shortClassName}")
        supportActionBar?.hide()
    }

    override fun onDestroy() {
        super.onDestroy()
        --activityCount
    }
}