package com.hugh.outsourcing.bank_acs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hugh.outsourcing.bank_acs.databinding.ActivityAssetBinding

class AssetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAssetBinding
    companion object{
        const val tag="AssetActivity"
        const val idx=1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAssetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.assetItems.dataFetch(this)
        navViewSetting()
    }
    private fun navViewSetting(){
        binding.navView.selectedItemId = binding.navView.getItemPosition(idx)
        binding.navView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navigation_home -> {
                    L.d(tag,"Jump to main")
                    startActivity(Intent(this,MainActivity::class.java))
                }
                R.id.navigation_dashboard -> {
                    L.d(tag,"select me again")
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