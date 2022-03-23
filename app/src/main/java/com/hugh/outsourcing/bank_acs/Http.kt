package com.hugh.outsourcing.bank_acs

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

object Http {
    // region paths
    private const val ip="http://150.158.77.38:12345"
    private const val all_prods = "/product/allProducts"
    private const val purchase = "/product/purchase"
    private const val loginPath = "/product/purchase"
    private const val history = "/product/purchaseHistory"
    private fun product(id:String) = "/product/${id}"
    private fun url(path:String) = "$ip/$path"
    // endregion
    private val httpd = OkHttpClient()

    private inline fun tokenBuilder(token:String):Request.Builder =
        Request.Builder()
            .addHeader("cache-control", "no-cache")
            .addHeader("Authorization" , "Bearer $token")
    private inline fun okhttp3.Callback.queue(req:Request) =
        httpd.newCall(req).enqueue(this)
    // region directly API
//    TODO API changed
//    fun getAllProducts()
    fun getProduct(id: String,token:String,callback: okhttp3.Callback){
        callback.queue(
            tokenBuilder(token)
                .url(url(product(id)))
                .get()
                .build()
        )
    }
    fun purchaseProduct(token:String,payload:okhttp3.FormBody,callback:okhttp3.Callback){
        callback.queue(
            tokenBuilder(token)
                .url(url(purchase))
                .put(payload)
                .build()
        )
    }
    fun purchaseHistory(token: String,callback:okhttp3.Callback){
        callback.queue(
            tokenBuilder(token)
                .url(history)
                .get()
                .build()
        )
    }
    fun login(payload:okhttp3.FormBody,callback:okhttp3.Callback){
        callback.queue(
            Request.Builder()
                .url(url(loginPath))
                .post(payload)
                .build()
        )
    }
    // endregion
    // region helper functions
    fun getLoginPayload(id:String,passwd:String):okhttp3.FormBody{
        return okhttp3.FormBody.Builder()
            .add("id",id)
            .add("password",passwd)
            .build()
    }
    fun getProductPayload(id:String,amount:Int):okhttp3.FormBody{
        return okhttp3.FormBody.Builder()
            .add("productId",id)
            .add("amount",amount.toString())
            .build()
    }
    // endregion
}