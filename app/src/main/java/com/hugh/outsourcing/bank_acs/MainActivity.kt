package com.hugh.outsourcing.bank_acs

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.reflect.TypeToken
import com.hugh.outsourcing.bank_acs.databinding.ActivityMainBinding
import com.hugh.outsourcing.bank_acs.service.Asset
import com.hugh.outsourcing.bank_acs.service.Product
import com.hugh.outsourcing.bank_acs.service.User
import com.hugh.outsourcing.bank_acs.vms.MainViewModel
import okhttp3.Call
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    private var current = R.id.navigation_home
    companion object{
        const val tag = "MainActivity"
        const val passwd = 1
        // promise use this obj when MainActivity alive
        @SuppressLint("StaticFieldLeak")
        var mMainContext: Context? = null
        val gson = com.google.gson.Gson()
    }
    // region get data
    private fun <T> getDataBase(token:String, failure:String, error:String,
                                assign:(List<T>)->Unit){
        Http.getAllProducts(token,object :okhttp3.Callback{
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    this@MainActivity.showToast(failure)
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val body = JSONObject(response.body!!.string())
                val code = body.getInt("code")
                if(code!=200){
                    runOnUiThread {
                        this@MainActivity.showToast(error)
                    }
                    L.i(tag,"code: $code")
                }else{
                    val productsJson = body.getJSONArray("data")
                    val type = object : TypeToken<List<T>>(){}.type
                    assign(gson.fromJson(productsJson.toString(),type))
                }
            }
        })
    }
    fun updateAllProducts(debug:Boolean = false, callback:()->Unit={}){
        if(debug){
            viewModel.products = productStub()
        }else{
            getDataBase<Product>(viewModel.token,"无法获取产品数据","获取产品数据失败"){
                    data -> viewModel.products = data
                    callback()
            }
        }
    }
    fun updateAssets(debug: Boolean = false, callback:()->Unit={}){
        if(debug){
            viewModel.assets = assetStub()
        }else{
            getDataBase<Asset>(viewModel.token,"无法获取资产数据","获取资产数据失败") {
                    data -> viewModel.assets = data
                    callback()
            }
        }
    }

    // endregion
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialization()
        setMainFragment()
        navViewSetting()
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            Http.logout(viewModel.token)
        }catch (ignored:UninitializedPropertyAccessException){}
        // avoid error if not login
    }
    // region viewModel Functions
    fun getToken():String = viewModel.token
    fun getProduct(id:String):Product{
        return viewModel.products.find { it.id == id }!!
    }
    fun getAsset(id:String):Asset{
        return viewModel.assets.find{ it.productId == id }!!
    }
    // endregion
    private fun initialization(){
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        setContentView(binding.root)
        mMainContext = this

        intent.let {
            viewModel.token = it.getStringExtra("token")!!
            viewModel.user = gson.fromJson(it.getStringExtra("user"),User::class.java)
        }
        updateAllProducts(true)
        updateAssets(true)
    }
    private fun setMainFragment(){
        val trans = supportFragmentManager.beginTransaction()
        trans.add(R.id.frame,HomeFragment.newInstance(viewModel.products))
        trans.commit()
    }
    private fun navigation(fragment:Fragment){
        L.e(tag,"before replacement fragments size:${supportFragmentManager.fragments.size}")
        val trans = supportFragmentManager.beginTransaction()
        trans.replace(R.id.frame,fragment)
        trans.commit()
        L.e(tag,"after replacement fragments size:${supportFragmentManager.fragments.size}")
    }
    private fun navViewSetting(){
        binding.navView.setOnItemSelectedListener {
            if(it.itemId==current) {
                L.d(tag,"click current fragment again")
                return@setOnItemSelectedListener true
            }
            when(it.itemId){
                R.id.navigation_home -> {
                    navigation(HomeFragment.newInstance(viewModel.products))
                    L.d(tag,"goto home")
                }
                R.id.navigation_dashboard -> {
                    L.d(tag,"Jump to dashboard")
                    navigation(AssetFragment.newInstance(viewModel.assets))
                    L.d(tag,"goto asset")
                }
                R.id.navigation_person -> {
                    L.d(tag,"Jump to personal page")
                    navigation(UserFragment.newInstance(viewModel.user.toPerson()))
                    L.d(tag,"goto user")
                }
            }
            L.d(tag,"change fragment")
            current = it.itemId
            true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            passwd->{}
        }
    }
}