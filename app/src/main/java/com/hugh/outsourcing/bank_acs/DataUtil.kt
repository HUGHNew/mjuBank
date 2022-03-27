package com.hugh.outsourcing.bank_acs

import android.content.Context
import android.content.Intent
import com.hugh.outsourcing.bank_acs.service.Product
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hugh.outsourcing.bank_acs.service.Asset

//fun dataStub():List<Pair<String,String>>{
//    return List(20){
//        "item${(100..200).random()}" to (10..90).random().toString()
//    }
//}
fun productStub(): List<Product> {
    return List(20){
        Product((1000..2000).random().toString(),"存款",(0..365).random(),0,0,100,1,1000,10,false,"")
    }
}
fun assetStub(): List<Asset>{
    return List(20){
        Asset((1..112313).random().toString(),"VIP 存储",(2..1231).random(),"TimeStamp")
    }
}

fun Context.getItemDetailIntent(mClass:Class<*>,name:String,rate:Double,unit:String,risk:String,method:String,
                        date:String,min:Int,max:Int): Intent {
    return Intent(this,mClass).apply {
        putExtra("name",name)
        putExtra("rate",rate)
        putExtra("unit",unit)
        putExtra("risk",risk)
        putExtra("method",method)
        putExtra("date",date)
        putExtra("min",min)
        putExtra("max",max)
    }
}