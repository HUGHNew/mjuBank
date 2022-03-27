package com.hugh.outsourcing.bank_acs.vms

import androidx.lifecycle.ViewModel
import com.hugh.outsourcing.bank_acs.service.User

class MainViewModel : ViewModel() {
    lateinit var products:MutableList<Pair<String,String>>
    lateinit var assets:MutableList<Pair<String,String>>
    lateinit var token:String
    var user: User? = null
}