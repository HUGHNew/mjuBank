package com.hugh.outsourcing.bank_acs

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.hugh.outsourcing.bank_acs.databinding.ActivityDetailHeaderBinding

class DetailHeader(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {
    private var binding: ActivityDetailHeaderBinding =
        ActivityDetailHeaderBinding.inflate(LayoutInflater.from(context))

    fun setContent(name:String,rate:Double,unit:String,risk:String,cal:String){
        binding.product.text=name
        (unit+resources.getString(R.string.detail_header_title)).also { binding.date.text = it }
        "$rate%".also { binding.rate.text = it }
        binding.riskLevel.text=risk
        binding.calMethod.text=cal
    }
    fun addLimiter(min:Int,max:Int){

    }
}