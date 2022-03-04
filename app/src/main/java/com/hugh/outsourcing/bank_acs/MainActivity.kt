package com.hugh.outsourcing.bank_acs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hugh.outsourcing.bank_acs.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    companion object{
        const val tag = "MainActivity"
        const val idx = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navViewSetting()
    }
    private fun navViewSetting(){
        binding.navView.selectedItemId = binding.navView.getItemPosition(idx)
        binding.navView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navigation_home -> {
                    L.d(tag,"select me again")
                }
                R.id.navigation_dashboard -> {
                    L.d(tag,"Jump to dashboard")
                    startActivity(Intent(this,AssetActivity::class.java))
                }
                R.id.navigation_person -> {
                    L.d(tag,"Jump to personal page")
                    when(Info.status){
                        Info.LoginStatus.NO -> {
                            L.d(tag,"not login yet")
                            startActivity(Intent(this,LoginActivity::class.java))
                        }
                        Info.LoginStatus.UNREAL,Info.LoginStatus.FULL ->{
                            startActivity(Intent(this,PersonActivity::class.java))
                        }
                    }
                }
            }
            false
        }
    }
}