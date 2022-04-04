package com.hugh.outsourcing.bank_acs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.TextView

class AssetDialog(context: Context,val title:String,val amount:String,val due:String)
    :Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_asset)
        setCancelable(false)

        findViewById<TextView>(R.id.title).text = title
        findViewById<TextView>(R.id.amount).text = amount
        findViewById<TextView>(R.id.due).text = due
        findViewById<Button>(R.id.ok).setOnClickListener { dismiss() }
    }
}