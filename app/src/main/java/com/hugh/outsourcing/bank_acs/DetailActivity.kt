package com.hugh.outsourcing.bank_acs

import android.app.Activity
import android.app.KeyguardManager
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hugh.outsourcing.bank_acs.databinding.ActivityDetailBinding
import com.hugh.outsourcing.bank_acs.service.Product
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailBinding
    lateinit var product: Product
    lateinit var helper: String
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
    private fun doPurchase(amount:Int, popup:Boolean = false){
        Http.purchaseProduct(token,
            Http.getProductPayload(product.id,amount),
            {response ->
                try {
                    val json = JSONObject(response.body!!.string())
                    L.d(tag,json.toString(2))
                    if (json.getInt("code") != 200){
                        val err = json.getString("data")
                        runOnUiThread {
                            if(popup){
                                showToast(err, toast_gravity = Gravity.CENTER)
                            }else{
                                setInputError(err)
                            }
                        }
                        setBuyResult(false)
                    }else{
                        if (product.showInterest){
                            val data = json.getJSONObject("data")
                            val interest = data.getDouble("interest")
                            val due = data.getString("due")
                            L.d(tag,"预期利息：$interest 到期时间：$due")
                            MaterialAlertDialogBuilder(this)
                                .setTitle("购买成功")
                                .setMessage("预期利息：$interest 到期时间：$due")
                                .setPositiveButton("确定") { _, _ ->}
                                .create()
                                .show()
                        }else{
                            if(popup){
                                runOnUiThread {
                                    showToast("购买成功", toast_gravity = Gravity.CENTER)
                                }
                            }
                        }
                        setBuyResult(true,amount)
                        finish()
                    }
                }catch (e:Exception){
                    // 应该到不了 就不改了
                    runOnUiThread {
                        showToast("购买出错:${e.message}", toast_gravity = Gravity.CENTER)
                    }
                    setBuyResult(false)
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
    private fun setInputError(err:String){
        binding.inputLay.error = err
        binding.inputLay.helperText = null
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

        helper = String.format(
            resources.getString(R.string.detail_helper),
            product.minAmount,product.maxAmount,product.incAmount
        )
        binding.inputLay.helperText = helper
        binding.input.addTextChangedListener {
            if (binding.inputLay.error!=null){
                binding.inputLay.error = null
                binding.inputLay.helperText = helper
            }
        }
        val dayOfSeconds: Long = 1000*60*60*24
        binding.dueDate.text = SimpleDateFormat("yyyy-MM-dd",Locale.CHINA).format(
            Date(System.currentTimeMillis()+product.period*(dayOfSeconds))
        )
        binding.confirm.setOnClickListener {
            L.d(tag,product.toString())
            val raw = binding.input.text.toString()
            if (raw == "" || raw.count { it=='.' } != 0) {
                runOnUiThread {
                    showToast("购买金额不在可购买范围内", toast_gravity = Gravity.CENTER)
                    setInputError("购买金额不在可购买范围内")
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
                        setInputError(product.getLastErr())
                    }
                    setBuyResult(false)
                }
            }
        }
    }
}