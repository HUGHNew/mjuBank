package com.hugh.outsourcing.bank_acs

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hugh.outsourcing.bank_acs.databinding.ActivityMainBinding
import com.hugh.outsourcing.bank_acs.service.Asset
import com.hugh.outsourcing.bank_acs.service.Product
import com.hugh.outsourcing.bank_acs.service.User
import com.hugh.outsourcing.bank_acs.vms.MainViewModel

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private var backPressTime:Long = 0
    val coster = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        L.d(tag,"finish purchase ${result.data?.toString()}")
        when(result.resultCode){
            Activity.RESULT_OK ->{
                val cost = result.data?.getIntExtra("cost",0)?:0
                L.d(tag,"it should cost $cost")
                viewModel.user.balance -= cost
                updateAssets{
                    L.d(tag,"update assets after purchase")
                }
            }
        }
        updateProducts{
            L.d(tag,"update products after start a detail")
        }
    }
    private var current = R.id.navigation_home
    companion object{
        const val tag = "MainActivity"
        const val ExitGap:Long = 2000 // millisecond
        // promise use this obj when MainActivity alive
        @SuppressLint("StaticFieldLeak")
        var mMainContext: Context? = null
        val gson = com.google.gson.Gson()
    }
    // region get data
    fun updateProducts(callback:(String)->Unit={}):List<Product>{
        return viewModel.updateProducts(callback)
    }
    fun updateAssets(callback:(String)->Unit={}):List<Asset>{
        return viewModel.updateAssets(callback)
    }

    // endregion
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialization()
        navViewSetting()
        setMainFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        L.d(tag,"logout now")
        try {
            Http.logout(viewModel.token,{response ->
                L.d(tag,"logout: $response body: ${response.body!!.string()}")
            })
        }catch (ignored:UninitializedPropertyAccessException){}
        // avoid error if not login
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - backPressTime < ExitGap){
            super.onBackPressed()
        }else{
            showToast("再按一次退出", toast_gravity = Gravity.CENTER)
            backPressTime = System.currentTimeMillis()
        }
    }
    // region viewModel Functions
    fun getToken():String = viewModel.token
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
        updateProducts{
            L.i(tag,"update products, msg:$it")
        }
        updateAssets{
            L.i(tag,"update assets, msg:$it")
        }
        L.e(tag,"products:${viewModel.products.value?.size}\tassets:${viewModel.assets.value?.size}")
    }

//    override fun onResume() {
//        super.onResume()
//        navigation(when(current){
//            R.id.navigation_home -> HomeFragment.newInstance(viewModel.products)
//            R.id.navigation_dashboard -> AssetFragment.newInstance(viewModel.assets,viewModel.user.balance)
//            R.id.navigation_person -> UserFragment.newInstance(viewModel.user.toPerson())
//            else -> HomeFragment.newInstance(viewModel.products)
//        })
//    }
    private fun setMainFragment(){
        val trans = supportFragmentManager.beginTransaction()
        val home = HomeFragment.newInstance(viewModel.products)
        trans.add(R.id.frame,home)
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
                    navigation(AssetFragment.newInstance(viewModel.assets,viewModel.user.balance))
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
}