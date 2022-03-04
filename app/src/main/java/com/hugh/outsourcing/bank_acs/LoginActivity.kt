package com.hugh.outsourcing.bank_acs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hugh.outsourcing.bank_acs.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    companion object{
        const val tag = "LoginActivity"
        const val noAccount = "请输入手机号"
        const val noPasswd = "请输入密码"
        const val noProtocol = "请同意协议"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Info.phone?.let {
            binding.apply {
                accountEditor.setText(it)
                protocol.isChecked = true
            }

        }
        binding.login.setOnClickListener {
            if(login()){
                finish()
            }
        }
    }
    private fun login():Boolean{
        L.w(tag,"phone number:${binding.accountEditor.text}")
        if (binding.accountEditor.text.isNullOrEmpty()){
            this.showToast(noAccount)
            return false
        }
        L.w(tag,"passwd:${binding.passwdEditor.text}")
        if(binding.passwdEditor.text.isNullOrEmpty()){
            this.showToast(noPasswd)
            return false
        }
        if(!binding.protocol.isChecked){
            this.showToast(noProtocol)
            return false
        }
        // check user and get info
        Info.status = Info.LoginStatus.UNREAL
        return true
    }
}