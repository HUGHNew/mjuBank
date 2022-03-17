package com.hugh.outsourcing.bank_acs

import androidx.lifecycle.ViewModel

class MainViewModel(): ViewModel() {
    companion object{
        const val shared = "Info"
    }
    enum class LoginStatus{
        NO,UNREAL,FULL
    }
    var status = LoginStatus.NO
    var init : Boolean = false
    var phone : String? = null
    var checked : Boolean = false
    lateinit var person: Person
}