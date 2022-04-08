package com.hugh.outsourcing.bank_acs

import android.app.Activity
import android.app.KeyguardManager
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.hugh.outsourcing.bank_acs.databinding.ActivityDetailBinding
import com.hugh.outsourcing.bank_acs.service.Product
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailBinding
    lateinit var product: Product
    private lateinit var token : String
    companion object{
        const val tag = "DetailActivity"
    }
    private fun getCredentialIntent(title:String, desc:String): Intent {
        val mKeyGuard = getSystemService(KeyguardManager::class.java)
        return mKeyGuard.createConfirmDeviceCredentialIntent(title,desc)
    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        L.d(tag,it.toString())
        val amount = binding.input.text.toString().toInt()
        if(it.resultCode == Activity.RESULT_OK){
            doPurchase(amount)
        }
    }
    private fun doPurchase(amount:Int){
        Http.purchaseProduct(
            token,Http.getProductPayload(product.id,amount),
            {response ->
                val json = JSONObject(response.body!!.string())
                if (json.getInt("code") != 200){
                    runOnUiThread {
                        showToast(json.getString("data"), toast_gravity = Gravity.CENTER)
                    }
                    setBuyResult(false)
                }else{
                    val data = json.getJSONObject("data")
                    val interest = data.getDouble("interest")
                    val due = data.getString("due")
                    L.d("PurchaseLog","预期利息：$interest 到期时间：$due")
                    if (product.showInterest){
                        AlertDialog.Builder(this)
                            .setTitle("购买成功")
                            .setMessage("预期利息：$interest 到期时间：$due")
                            .setPositiveButton("确定") { _, _ ->}
                            .create()
                            .show()
                    }else{
                        runOnUiThread {
                            showToast("购买成功", toast_gravity = Gravity.CENTER)
                        }
                    }
                    setBuyResult(true,amount)
                    finish()
                }
            }
        )
    }
    private fun setBuyResult(ok:Boolean,cost:Int = 0){
        if (ok){
            setResult(Activity.RESULT_OK,Intent().apply {
                putExtra("cost",cost)
            })
            L.d(tag,"Cost is $cost")
        }else{
            setResult(Activity.RESULT_CANCELED)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialization()
    }

    private fun initialization(){
        when(intent.getIntExtra("type",0)){
            0 ->{ // product
                token = intent.getStringExtra("token")!!
                product = intent.getSerializableExtra("product") as Product
                // header
                binding.product.text = product.name
                binding.rate.text = (product.annualRate/100.0).toString()
                binding.buyAmount.text = product.dailyAmount.toString()
            }
        }
        val dayOfSeconds: Long = 1000*60*60*24
        binding.dueDate.text = SimpleDateFormat("yyyy-MM-dd",Locale.CHINA).format(
            Date(System.currentTimeMillis()+product.period*(dayOfSeconds))
        )
        binding.input.hint = "购买金额(${product.minAmount}-${product.maxAmount})/${product.incAmount}"
        binding.confirm.setOnClickListener {
            L.d(tag,product.toString())
            val raw = binding.input.text.toString()
            if (raw == "" || raw.count { it=='.' } != 0) {
                runOnUiThread {
                    showToast("购买金额不在可购买范围内", toast_gravity = Gravity.CENTER)
                }
                setBuyResult(false)
            }else {
                val amount = raw.toInt()
                if(product.isPurchasable(amount)) {
                    if(product.clientCheck) {
                        launcher.launch(getCredentialIntent("购买产品", "身份验证"))
                    }
                    doPurchase(amount)
                }else {
                    runOnUiThread {
                        showToast(product.getLastErr(), toast_gravity = Gravity.CENTER)
                    }
                    setBuyResult(false)
                }
            }
        }
    }
}