package com.hugh.outsourcing.bank_acs.vms

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hugh.outsourcing.bank_acs.Http
import com.hugh.outsourcing.bank_acs.L
import com.hugh.outsourcing.bank_acs.MainActivity
import com.hugh.outsourcing.bank_acs.service.Asset
import com.hugh.outsourcing.bank_acs.service.Product
import com.hugh.outsourcing.bank_acs.service.User
import org.json.JSONObject

class MainViewModel : ViewModel() {
    private val __products = MutableLiveData<List<Product>>()
    val products:LiveData<List<Product>>
        get() = __products
//    var products:List<Product>
    private val __assets = MutableLiveData<List<Asset>>()
    val assets: LiveData<List<Asset>>
        get() = __assets
//    var assets:List<Asset>
    lateinit var token:String
    lateinit var user: User
    fun updateProducts(callback:(String)->Unit={}):List<Product>{
        var result : List<Product> = listOf()
        Http.getAllProducts(token,{ response ->
            val msg: String
            val body = JSONObject(response.body!!.string())
            val code = body.getInt("code")
            if(code!=200){
                msg = "获取商品数据失败"
                L.i(MainActivity.tag,"code: $code")
            }else{
                val productsJson = body.getJSONArray("data")
                val type = object : TypeToken<List<Product>>(){}.type
                L.d(MainActivity.tag,productsJson.toString())
                result = Gson().fromJson(productsJson.toString(),type)
                __products.postValue(result)
                msg = "成功获取商品:${result.size}"
            }
            callback(msg)
        })
        return result
    }
    fun updateAssets(callback: (String) -> Unit):List<Asset>{
        var result : List<Asset> = listOf()
        Http.purchaseHistory(token,{response ->
            val msg :String
            val body = JSONObject(response.body!!.string())
            val code = body.getInt("code")
            if(code!=200){
                msg = "获取资产数据失败"
                L.i(MainActivity.tag,"code: $code")
            }else{
                val assetsJson = body.getJSONArray("data")
                L.d(MainActivity.tag,assetsJson.toString())
                val type = object : TypeToken<List<Asset>>(){}.type
                result = Gson().fromJson(assetsJson.toString(),type)
                __assets.postValue(result)
                msg = "成功获取资产数据:${__assets.value?.size}"
            }
            callback(msg)
        })
        return result
    }
}