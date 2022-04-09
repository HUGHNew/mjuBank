package com.hugh.outsourcing.bank_acs.vms

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hugh.outsourcing.bank_acs.Http
import com.hugh.outsourcing.bank_acs.L
import com.hugh.outsourcing.bank_acs.service.Product
import okhttp3.Call
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class HomeViewModel : ViewModel() {
    companion object{
        const val tag = "HomeViewModel"
    }
    val products = MutableLiveData<List<Product>>()
    fun updateProducts(token:String,callback:(String)->Unit={}):List<Product>{
        var result : List<Product> = listOf()
        Http.getAllProductsAsync(token,object :okhttp3.Callback{
            override fun onFailure(call: Call, e: IOException) {}

            override fun onResponse(call: Call, response: Response) {
                updateSucceed(response, callback)
            }
        })
        return result
    }
    fun updateSucceed(response:Response,callback:(String)->Unit={}){
        val msg: String
        val body = JSONObject(response.body!!.string())
        val code = body.getInt("code")
        if(code!=200){
            msg = "获取商品数据失败"
            L.i(tag,"code: $code")
        }else{
            val productsJson = body.getJSONArray("data")
            val type = object : TypeToken<List<Product>>(){}.type
            L.d(tag,productsJson.toString())
            products.postValue(Gson().fromJson(productsJson.toString(),type))
            msg = "成功获取商品:${products.value?.size}"
//            result = Gson().fromJson(productsJson.toString(),type)
//            products.postValue(result)
//            msg = "成功获取商品:${result.size}"
        }
        callback(msg)
    }
}