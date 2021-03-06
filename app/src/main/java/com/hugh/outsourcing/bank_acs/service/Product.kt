package com.hugh.outsourcing.bank_acs.service

import com.google.gson.Gson
import com.hugh.outsourcing.bank_acs.*
import java.io.Serializable


class Product(val id:String, val name:String, val period:Int, val annualRate:Int,
              val minAmount:Int,val maxAmount:Int,val incAmount:Int,
              val dailyAmount:Int, val surplus:Int,
              val showInterest:Boolean = false,val clientCheck:Boolean = false):Serializable{
    private var already:Int = 0
    private var errCode:Int = 0
    companion object{
        val errors = listOf(
            "购买金额不在范围内",
            "购买金额超过当天限制",
            "当前金额不规范,请注意起增金额",
            "当前产品不可购买"
        )
    }
    fun getLastErr():String = errors[errCode-1]
    fun purchase(amount: Int){
        already += amount
    }
    fun isPurchasable(amount: Int):Boolean{
        errCode = purchaseStatus(amount)
        return errCode == 0
    }
    fun validate(user: User):Boolean{
        return true
    }
    private fun purchaseStatus(amount: Int):Int{
        L.d("Purchase","$amount $minAmount-$maxAmount~$incAmount")
        if(amount !in minAmount..maxAmount)return 1
        if(amount + already > dailyAmount)return 2
        if((amount-minAmount) % incAmount != 0)return 3
        return 0
    }

    override fun toString(): String {
        return Gson().toJson(this)
    }
}