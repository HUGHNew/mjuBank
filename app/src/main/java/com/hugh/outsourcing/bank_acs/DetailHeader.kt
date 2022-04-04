package com.hugh.outsourcing.bank_acs

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.hugh.outsourcing.bank_acs.databinding.ActivityDetailHeaderBinding

class DetailHeader(context: Context, attrs: AttributeSet)
    : ConstraintLayout(context, attrs) {
    init {
        LayoutInflater.from(context).inflate(R.layout.activity_detail_header,this)
//        setContent(name, rate, unit, risk, cal)
    }
    private var binding: ActivityDetailHeaderBinding =
        ActivityDetailHeaderBinding.inflate(LayoutInflater.from(context))

    fun setContent(name:String,rate:Double,unit:String,risk:String,cal:String){
        L.d("Detail","pass value")
        binding.product.text=name
        binding.date.text =  unit+resources.getString(R.string.detail_header_title)
        binding.rate.text = "$rate%"
        binding.riskLevel.text=risk
        binding.calMethod.text=cal
    }
}