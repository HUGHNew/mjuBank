package com.hugh.outsourcing.bank_acs

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity:AppCompatActivity() {
    companion object{
        var activityCount = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(activityCount == 0){
            readInfo()
        }
        ++activityCount
        supportActionBar?.hide()
    }

    override fun onDestroy() {
        super.onDestroy()
        --activityCount
        if(activityCount == 0){
            saveInfo()
        }
    }
}