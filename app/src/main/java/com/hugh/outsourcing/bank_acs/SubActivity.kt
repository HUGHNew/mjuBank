package com.hugh.outsourcing.bank_acs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class SubActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)
        findViewById<Button>(R.id.stub).setOnClickListener {
            val intent = Intent(this,MainActivity::class.java).apply {
                putExtra("token","123")
                putExtra("user","{\n" +
                        "        \"name\": \"刘五\",\n" +
                        "        \"balance\": 600000,\n" +
                        "        \"area\": \"湖南\",\n" +
                        "        \"validity\": \"2020-10-01\"\n" +
                        "    }\n")
            }
            startActivity(intent)
            finish()
        }
    }
}