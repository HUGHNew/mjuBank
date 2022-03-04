package com.hugh.outsourcing.bank_acs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hugh.outsourcing.bank_acs.databinding.ActivityAuthBinding

class AuthActivity : BaseActivity() {
    private lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}