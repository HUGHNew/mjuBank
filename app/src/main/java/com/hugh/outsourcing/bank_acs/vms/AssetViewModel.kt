package com.hugh.outsourcing.bank_acs.vms

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hugh.outsourcing.bank_acs.Http
import com.hugh.outsourcing.bank_acs.L
import com.hugh.outsourcing.bank_acs.MainActivity
import com.hugh.outsourcing.bank_acs.service.Asset
import okhttp3.Call
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class AssetViewModel : ViewModel() {
    val assets = MutableLiveData<List<Asset>>()
    fun updateAssets(token:String,callback: (String) -> Unit):List<Asset>{
        var result : List<Asset> = listOf()
        Http.purchaseHistoryAsync(token,object:okhttp3.Callback{
            override fun onFailure(call: Call, e: IOException) {}

            override fun onResponse(call: Call, response: Response) {
                updateSucceed(response,callback)
            }
        })
        return result
    }
    fun updateSucceed(response:Response,callback: (String) -> Unit){
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
            assets.postValue(Gson().fromJson(assetsJson.toString(),type))
            msg = "成功获取资产数据:${assets.value?.size}"
//            result = Gson().fromJson(assetsJson.toString(),type)
//            assets.postValue(result)
//            msg = "成功获取资产数据:${result.size}"
        }
        callback(msg)
    }
}