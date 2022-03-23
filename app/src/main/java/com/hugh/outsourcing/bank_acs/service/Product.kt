package com.hugh.outsourcing.bank_acs.service

import com.hugh.outsourcing.bank_acs.*


class Product(val id:String, val name:String, val period:Int, val annual:Int,
              private val minAmount:Int, private val maxAmount:Int, private val incAmount:Int,
              private val dailyAmount:Int, private val riskLevel:Int, val service:String){
    private val already:Int = 0
    companion object{
        val errors = listOf(
            "购买金额不在范围内",
            "购买金额超过当天限制",
            "当前金额不规范,请注意起增金额"
        )
        val risks = listOf("低风险","中风险","高风险")
    }
    fun getRisk():String = risks[riskLevel]
    fun purchase(amount: Int): Boolean {
        return when (val code = purchaseStatus(amount)){
            0 -> {
                // TODO send msg
                true
            }
            else ->{
                MainActivity.mMainContext?.showToast(errors[code-1])
                false
            }
        }
    }
    fun validate(user: User):Boolean{
        return true
    }
    private fun purchaseStatus(amount: Int):Int{
        if(amount !in minAmount..maxAmount)return 1
        if(amount + already > dailyAmount)return 2
        if((amount-minAmount) % incAmount != 0)return 3
        return 0
    }
}