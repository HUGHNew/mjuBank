package com.hugh.outsourcing.bank_acs.vms

import androidx.lifecycle.ViewModel

class MainViewModel(): ViewModel() {
    lateinit var products:MutableList<Pair<String,String>>
    lateinit var assets:MutableList<Pair<String,String>>
}