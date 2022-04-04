package com.hugh.outsourcing.bank_acs.service

import java.io.Serializable

data class Asset(val productId:String, val productName:String,
                 val amount: Int,val purchaseDate:String): Serializable