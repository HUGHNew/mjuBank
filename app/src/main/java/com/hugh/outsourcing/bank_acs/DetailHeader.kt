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
}