package com.hugh.outsourcing.bank_acs

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hugh.outsourcing.bank_acs.databinding.ActivityMainBinding
import com.hugh.outsourcing.bank_acs.service.User
import com.hugh.outsourcing.bank_acs.vms.MainViewModel

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private var current = R.id.navigation_home
    companion object{
        const val tag = "MainActivity"
        const val passwd = 1
        const val login  = 2
        // promise use this obj when MainActivity alive
        @SuppressLint("StaticFieldLeak")
        var mMainContext: Context? = null
        val gson = com.google.gson.Gson()
        enum class LoginStatus{
            NO,UNREAL,FULL
        }
        var status = LoginStatus.NO
    }
    // viewModel is better

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setContentView(binding.root)
        mMainContext = this
        setMainFragment()
        navViewSetting()
    }
    private fun setMainFragment(){
        val trans = supportFragmentManager.beginTransaction()
//        trans.add(R.id.frame,HomeFragment.newInstance(viewModel.products))
        trans.add(R.id.frame,HomeFragment.newInstance(mutableListOf()))
        // get data
        viewModel.products = dataStub() as MutableList<Pair<String, String>>
        trans.commit()
    }
    private fun navigation(fragment:Fragment){
        L.e(tag,"before replacement fragments size:${supportFragmentManager.fragments.size}")
        val trans = supportFragmentManager.beginTransaction()
        trans.replace(R.id.frame,fragment)
        trans.commit()
        L.e(tag,"after replacement fragments size:${supportFragmentManager.fragments.size}")
    }
    private fun navViewSetting(){
        binding.navView.setOnItemSelectedListener {
            if(it.itemId==current) {
                L.d(tag,"click current fragment again")
                return@setOnItemSelectedListener true
            }
            when(it.itemId){
                R.id.navigation_home -> {
                    navigation(HomeFragment.newInstance(viewModel.products))
                    L.d(tag,"goto home")
                }
                R.id.navigation_dashboard -> {
                    L.d(tag,"Jump to dashboard")
                    when(status){
                        LoginStatus.NO -> {
                            L.d(tag,"not login yet")
                            startActivityForResult(Intent(this,LoginActivity::class.java),login)
                            return@setOnItemSelectedListener false
                        }
                        LoginStatus.UNREAL,LoginStatus.FULL ->{
                            navigation(AssetFragment.newInstance())
                            L.d(tag,"goto asset")
                        }
                    }
                }
                R.id.navigation_person -> {
                    L.d(tag,"Jump to personal page")
                    when(status){
                        LoginStatus.NO -> {
                            L.d(tag,"not login yet")
                            startActivityForResult(Intent(this,LoginActivity::class.java),login)
                            return@setOnItemSelectedListener false
                        }
                        LoginStatus.UNREAL,LoginStatus.FULL ->{
                            navigation(UserFragment.newInstance())
                            L.d(tag,"goto user")
                        }
                    }
                }
            }
            L.d(tag,"change fragment")
            current = it.itemId
            true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            passwd->{}
            login->{
                if(resultCode==Activity.RESULT_OK){
                    data?.let {
                        L.d(tag,"login finish")
                        status = LoginStatus.FULL
                        viewModel.user = gson.fromJson(it.getStringExtra("data"),User::class.java)
                        viewModel.token = it.getStringExtra("token")!!
                    }
                }
            }
        }
    }
}