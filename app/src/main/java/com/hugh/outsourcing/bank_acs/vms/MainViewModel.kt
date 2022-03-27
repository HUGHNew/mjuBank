package com.hugh.outsourcing.bank_acs.vms

import androidx.lifecycle.ViewModel
import com.hugh.outsourcing.bank_acs.service.Asset
import com.hugh.outsourcing.bank_acs.service.Product
import com.hugh.outsourcing.bank_acs.service.User

class MainViewModel : ViewModel() {
    lateinit var products:List<Product>
    lateinit var assets:List<Asset>
    lateinit var token:String
    lateinit var user: User
}