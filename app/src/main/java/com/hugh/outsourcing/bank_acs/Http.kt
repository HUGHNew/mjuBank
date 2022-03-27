package com.hugh.outsourcing.bank_acs

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

object Http {
    // region paths
    private const val ip="http://150.158.77.38:12345"
    private const val all_prods = "/product/allProducts"
    private const val purchase = "/product/purchase"
    private const val loginPath = "/user/login"
    private const val history = "/product/purchaseHistory"
    private fun product(id:String) = "/product/${id}"
    private fun url(path:String) = "$ip$path"
    private val Media = "application/json; charset=utf-8".toMediaTypeOrNull()
    // endregion
    private val httpd = OkHttpClient()

    private fun tokenBuilder(token:String):Request.Builder =
        Request.Builder()
            .addHeader("cache-control", "no-cache")
            .addHeader("Content-Type","application/json")
            .addHeader("Authorization" , "Bearer $token")
    private fun Callback.queue(req:Request) =
        httpd.newCall(req).enqueue(this)
    // region directly API
//    TODO API changed
    fun getAllProducts(token:String,callback: Callback){
        callback.queue(
            tokenBuilder(token)
                .url(url(all_prods))
                .get()
                .build()
        )
    }
    fun getProduct(id: String,token:String,callback: Callback){
        callback.queue(
            tokenBuilder(token)
                .url(url(product(id)))
                .get()
                .build()
        )
    }
    fun purchaseProduct(token:String, payload: RequestBody, callback: Callback){
        callback.queue(
            tokenBuilder(token)
                .url(url(purchase))
                .put(payload)
                .build()
        )
    }
    fun purchaseHistory(token: String,callback: Callback){
        callback.queue(
            tokenBuilder(token)
                .url(history)
                .get()
                .build()
        )
    }
    fun login(payload: RequestBody, callback: Callback){
        callback.queue(
            Request.Builder()
                .addHeader("Content-Type","application/json; charset=utf-8")
                .url(url(loginPath))
                .post(payload)
                .build()
        )
    }
    // endregion
    // region helper functions
    fun getLoginPayload(id:String,passwd:String): RequestBody{
        return JSONObject().apply {
            put("id",id)
            put("password",passwd)
        }.toString().toRequestBody(Media)
    }
    fun getProductPayload(id:String,amount:Int): RequestBody{
        return JSONObject().apply {
            put("productId",id)
            put("amount",amount.toString())
        }.toString().toRequestBody(Media)
    }
    // endregion
}