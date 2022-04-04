package com.hugh.outsourcing.bank_acs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import com.hugh.outsourcing.bank_acs.databinding.ActivityDetailBinding
import com.hugh.outsourcing.bank_acs.service.Product
import okhttp3.Call
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    lateinit var product: Product
    lateinit var token : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialization()
    }
    private fun initialization(){
        when(intent.getIntExtra("type",0)){
            0 ->{
                // product
                token = intent.getStringExtra("token")!!
                product = intent.getSerializableExtra("product") as Product
                binding.header.setContent(product.name,product.annual/100.0,"年",
                    "余量: ${product.surplus}","emm")
            }
            1 ->{}
        }
        binding.confirm.setOnClickListener {
            val raw = binding.input.text.toString()
            val limit = product.dailyAmount.toString()
            if (raw == "" || raw.count { it=='.' } != 0
                || raw.length > product.dailyAmount.toString().length||raw[0]>limit[0]) {
                // over limit
                runOnUiThread {
                    showToast("购买金额不在可购买范围内", toast_gravity = Gravity.TOP)
                }
            }else {
                val amount = raw.toInt()
                if(product.isPurchasable(amount)) {
                    Http.purchaseProduct(token,Http.getProductPayload(product.id,amount),
                        object :okhttp3.Callback{
                            override fun onFailure(call: Call, e: IOException) {
                                TODO("Not yet implemented")
                            }

                            override fun onResponse(call: Call, response: Response) {
                                val json = JSONObject(response.body!!.string())
                                if (json.getInt("code") != 200){
                                    runOnUiThread {
                                        showToast(json.getString("data"))
                                    }
                                }else{
                                    val data = json.getJSONObject("data")
                                    val interest = data.getDouble("interest")
                                    val due = data.getString("due")
                                    runOnUiThread {
                                        showToast("预期利息：$interest 到期时间：$due")
                                        L.d("PurchaseLog","预期利息：$interest 到期时间：$due")
                                    }
                                }
                            }
                        })
                }else {
                    runOnUiThread {
                        showToast(product.getLastErr(), toast_gravity = Gravity.TOP)
                    }
                }
            }
        }
    }
}