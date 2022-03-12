package com.hugh.outsourcing.bank_acs

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun dataStub():List<Pair<String,String>>{
    return List(20){
        "item${(100..200).random()}" to (10..90).random().toString()
    }
}

fun RecyclerView.dataFetch(context: Context,init:Boolean = true):List<Pair<String,String>>{
    var result : List<Pair<String,String>>? =null
    if(init){
        val data = dataStub()
        layoutManager = LinearLayoutManager(context)
        adapter = ListItemAdapter(data)
        result = data
    }else{
        // update
    }
    L.d("data-stub","result count:${result?.size}")
    L.d("data-stub","last record:${result?.last()}")
    return result!!
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