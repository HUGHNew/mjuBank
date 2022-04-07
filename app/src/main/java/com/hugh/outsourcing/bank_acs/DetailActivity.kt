package com.hugh.outsourcing.bank_acs

import android.app.Activity
import android.app.KeyguardManager
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.hugh.outsourcing.bank_acs.databinding.ActivityDetailBinding
import com.hugh.outsourcing.bank_acs.service.Product
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : BaseActivity() {
    lateinit var binding: ActivityDetailBinding
    lateinit var product: Product
    lateinit var token : String
    var allow = true
    fun getCredentialIntent(title:String,desc:String): Intent {
        val mKeyGuard = getSystemService(KeyguardManager::class.java)
        return mKeyGuard.createConfirmDeviceCredentialIntent(title,desc)
    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        allow = it.resultCode == Activity.RESULT_OK
    }
    private fun setBuyResult(ok:Boolean,cost:Int = 0){
        if (ok){
            setResult(Activity.RESULT_OK,Intent().apply {
                putExtra("cost",cost)
            })
            L.d("DetailActivity","Cost is $cost")
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

    fun setContent(name:String,rate:Double,unit:String,risk:String,cal:String){
        L.d("Detail","pass value")
        binding.product.text=name
        binding.date.text =  unit+resources.getString(R.string.detail_header_title)
        binding.rate.text = "$rate%"
        binding.riskLevel.text=risk
        binding.calMethod.text=cal
    }
    private fun initialization(){
        when(intent.getIntExtra("type",0)){
            0 ->{ // product
                token = intent.getStringExtra("token")!!
                product = intent.getSerializableExtra("product") as Product
                setContent(product.name,product.annual/100.0,"年",
                    "余量: ${product.surplus}","emm")
            }
        }
        // TODO purchase + period
        binding.dueDate.text = SimpleDateFormat("yyyy/MM/dd",Locale.CHINA).format(
            Date(System.currentTimeMillis()+product.period*(1000*60*60*24))
        )
        binding.input.hint = "购买金额(${product.minAmount}-${product.maxAmount})/${product.incAmount}"
        binding.confirm.setOnClickListener {
            val raw = binding.input.text.toString()
            val limit = product.dailyAmount.toString()
            if (raw == "" || raw.count { it=='.' } != 0) {
                //|| raw.length > product.dailyAmount.toString().length||raw[0]>limit[0] //??
                // over limit
                runOnUiThread {
                    showToast("购买金额不在可购买范围内", toast_gravity = Gravity.TOP)
                }
                setBuyResult(false)
            }else {
                val amount = raw.toInt()
                if(product.isPurchasable(amount)) {
                    if(product.clientCheck) {
                        launcher.launch(getCredentialIntent("购买产品", "身份验证"))
                    }
                    if(allow){
                        Http.purchaseProduct(
                            token,Http.getProductPayload(product.id,amount),
                            {response ->
                                val json = JSONObject(response.body!!.string())
                                if (json.getInt("code") != 200){
                                    runOnUiThread {
                                        showToast(json.getString("data"))
                                    }
                                    setBuyResult(false)
                                }else{
                                    val data = json.getJSONObject("data")
                                    val interest = data.getDouble("interest")
                                    val due = data.getString("due")
                                    runOnUiThread {
                                        showToast("预期利息：$interest 到期时间：$due",
                                            toast_gravity = Gravity.CENTER)
                                        L.d("PurchaseLog","预期利息：$interest 到期时间：$due")
                                    }
                                    setBuyResult(true,amount)
                                    finish()
                                }
                            }
                        )
                    }
                }else {
                    runOnUiThread {
                        showToast(product.getLastErr(), toast_gravity = Gravity.TOP)
                    }
                    setBuyResult(false)
                }
            }
        }
    }
}