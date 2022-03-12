package com.hugh.outsourcing.bank_acs

object Info{
    const val shared = "Info"
    enum class LoginStatus{
        NO,UNREAL,FULL
    }
    var status = LoginStatus.NO
    var init : Boolean = false
    var phone : String? = null
    var checked : Boolean = false
    lateinit var person: Person
}