package com.hugh.outsourcing.bank_acs

import android.app.KeyguardManager
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hugh.outsourcing.bank_acs.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private var current = R.id.navigation_home
    companion object{
        const val tag = "MainActivity"
        const val passwd = 1
    }
    // viewModel is better

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setContentView(binding.root)
        setMainFragment()
        navViewSetting()
    }
    private fun setMainFragment(){
        val trans = supportFragmentManager.beginTransaction()
        trans.add(R.id.frame,HomeFragment.newInstance())
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
            }else{
                val mKeyGuard = getSystemService(KeyguardManager::class.java)
                val intent = mKeyGuard.createConfirmDeviceCredentialIntent(null,null)
                intent?.let {itt -> startActivityForResult(itt, passwd) }
            }
            when(it.itemId){
                R.id.navigation_home -> {
                    navigation(HomeFragment.newInstance())
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

    }
}