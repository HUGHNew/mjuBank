package com.hugh.outsourcing.bank_acs

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hugh.outsourcing.bank_acs.databinding.ActivityMainBinding
import com.hugh.outsourcing.bank_acs.vms.MainViewModel

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private var current = R.id.navigation_home
    companion object{
        const val tag = "MainActivity"
        const val passwd = 1
        // promise use this obj when MainActivity alive
        @SuppressLint("StaticFieldLeak")
        var mMainContext: Context? = null
        val gson = com.google.gson.Gson()
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
        trans.add(R.id.frame,HomeFragment.newInstance(viewModel.products))
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
                    when(Info.status){
                        Info.LoginStatus.NO -> {
                            L.d(tag,"not login yet")
                            startActivity(Intent(this,LoginActivity::class.java))
                            return@setOnItemSelectedListener false
                        }
                        Info.LoginStatus.UNREAL,Info.LoginStatus.FULL ->{
                            navigation(AssetFragment.newInstance())
                            L.d(tag,"goto asset")
                        }
                    }
                }
                R.id.navigation_person -> {
                    L.d(tag,"Jump to personal page")
                    when(Info.status){
                        Info.LoginStatus.NO -> {
                            L.d(tag,"not login yet")
                            startActivity(Intent(this,LoginActivity::class.java))
                            return@setOnItemSelectedListener false
                        }
                        Info.LoginStatus.UNREAL,Info.LoginStatus.FULL ->{
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
        }
    }
}