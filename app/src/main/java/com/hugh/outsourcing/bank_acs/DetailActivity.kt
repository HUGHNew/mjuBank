package com.hugh.outsourcing.bank_acs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hugh.outsourcing.bank_acs.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
    fun setContent(name:String,rate:Double,unit:String,risk:String,cal:String){
        binding.header.product.text=name
        (unit+resources.getString(R.string.detail_header_title)).also { binding.header.date.text = it }
        "$rate%".also { binding.header.rate.text = it }
        binding.header.riskLevel.text=risk
        binding.header.calMethod.text=cal
    }
    fun addLimiter(min:Int,max:Int){

    }
}