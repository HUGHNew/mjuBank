package com.hugh.outsourcing.bank_acs.vms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hugh.outsourcing.bank_acs.Http
import com.hugh.outsourcing.bank_acs.L
import com.hugh.outsourcing.bank_acs.MainActivity
import com.hugh.outsourcing.bank_acs.service.Asset
import com.hugh.outsourcing.bank_acs.service.Product
import com.hugh.outsourcing.bank_acs.service.User
import org.json.JSONObject

class MainViewModel : ViewModel() {
    lateinit var token:String
    lateinit var user: User
}