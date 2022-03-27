package com.hugh.outsourcing.bank_acs

import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.widget.Toast
import androidx.core.content.edit
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hugh.outsourcing.bank_acs.service.Asset
import com.hugh.outsourcing.bank_acs.service.Product
import com.hugh.outsourcing.bank_acs.service.User

// region UI Partition
fun BottomNavigationView.getItemPosition(pos : Int):Int =  menu.getItem(pos).itemId

fun BaseActivity.startPasswdValidation(title:String?=null,desc:String?=null){
    val mKeyGuard = getSystemService(KeyguardManager::class.java)
    val intent = mKeyGuard.createConfirmDeviceCredentialIntent(title,desc)
    intent?.let {itt -> startActivityForResult(itt, MainActivity.passwd) }
}

fun Context.showToast(msg:String,longTime:Boolean = false){
    Toast.makeText(this,msg,
        if(longTime)Toast.LENGTH_LONG
        else Toast.LENGTH_SHORT
    ).show()
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