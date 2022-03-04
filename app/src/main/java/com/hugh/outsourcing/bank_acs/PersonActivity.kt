package com.hugh.outsourcing.bank_acs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hugh.outsourcing.bank_acs.databinding.ActivityPersonBinding

class PersonActivity : BaseActivity() {
    private lateinit var binding : ActivityPersonBinding
    companion object{
        const val tag="AssetActivity"
        const val idx=2
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                    L.d(tag,"Jump to asset page")
                    startActivity(Intent(this,AssetActivity::class.java))
                }
                R.id.navigation_person -> {
                    L.d(tag,"select me again")
                }
            }
            false
        }
    }
}