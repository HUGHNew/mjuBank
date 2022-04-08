package com.hugh.outsourcing.bank_acs

import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.view.Gravity
import android.widget.Toast
import androidx.core.content.edit
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hugh.outsourcing.bank_acs.service.Asset
import com.hugh.outsourcing.bank_acs.service.Product
import com.hugh.outsourcing.bank_acs.service.User

// region UI Partition
fun BottomNavigationView.getItemPosition(pos : Int):Int =  menu.getItem(pos).itemId

fun Context.showToast(msg:String,longTime:Boolean = false,toast_gravity: Int = Gravity.BOTTOM){
    Toast.makeText(this,msg,
        if(longTime)Toast.LENGTH_LONG
        else Toast.LENGTH_SHORT
    ).apply{
        setGravity(toast_gravity,0,0)
    }.show()
}
fun Activity.readPhone():String?{
    return with(getSharedPreferences(LoginActivity.shared,Context.MODE_PRIVATE)){
        getString("phone",null)
    }
}
fun Activity.savePhone(number:String){
    getSharedPreferences(LoginActivity.shared,Context.MODE_PRIVATE).edit{
        putString("phone",number)
    }
}
// endregion

// region classes
fun User.toPerson():Person = Person(name,validity)
fun Product.simplify():Pair<String,String> = name to surplus.toString()
fun Asset.simplify():Pair<String,String> = productName to purchaseDate
// endregion