package com.hugh.outsourcing.bank_acs

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView

fun BottomNavigationView.getItemPosition(pos : Int):Int =  menu.getItem(pos).itemId

fun Context.showToast(msg:String,longTime:Boolean = false){
    Toast.makeText(this,msg,
        if(longTime)Toast.LENGTH_LONG
        else Toast.LENGTH_SHORT
    ).show()
}
fun Activity.readInfo(){
    if(!Info.init){ // need init
        Info.init = true
        Info.checked = true
        getSharedPreferences(Info.shared,Context.MODE_PRIVATE).apply {
            Info.phone = getString("phone",null)
        }
    }
}
fun Activity.saveInfo(){
    if(Info.status!=Info.LoginStatus.NO){
        getSharedPreferences(Info.shared,Context.MODE_PRIVATE).edit().apply{
            putString("phone",Info.phone)
            apply()
        }
    }
}