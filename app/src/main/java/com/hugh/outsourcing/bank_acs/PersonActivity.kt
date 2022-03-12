package com.hugh.outsourcing.bank_acs

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hugh.outsourcing.bank_acs.databinding.ActivityPersonBinding

class PersonActivity : BaseActivity() {
    private lateinit var binding : ActivityPersonBinding
    companion object{
        const val tag="AssetActivity"
        const val idx=2

        const val GET_ID_DUE = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPersonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navViewSetting()
        infoLoad()
    }
    private fun infoLoad(){
        binding.name.text = Info.phone!! // last 4 number
        setRealInfo()
        binding.auth.setOnClickListener {
            startActivityForResult(Intent(this,AuthActivity::class.java), GET_ID_DUE)
        }
    }

    private fun setRealInfo(){
        if (Info.person.realName.isEmpty()){
            binding.auth.text = "尚未实名认证 点击可添加实名信息"
        }else{
            binding.auth.text = "已实名\n${Info.person.realName}到期(点击可修改)"
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            GET_ID_DUE ->{
                if(resultCode==Activity.RESULT_OK){
                    data?.let {
                        Info.person.realName = it.getStringExtra("id").toString()
                        setRealInfo()
                    }
                }
            }
        }
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