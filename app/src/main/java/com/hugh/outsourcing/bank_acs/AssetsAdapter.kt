package com.hugh.outsourcing.bank_acs

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hugh.outsourcing.bank_acs.service.Asset

class AssetsAdapter(private val items:List<Asset>):
    RecyclerView.Adapter<AssetsAdapter.ViewHolder>(){
    inner  class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val title : TextView = view.findViewById(R.id.title)
        val desc : TextView = view.findViewById(R.id.desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.productName
        holder.desc.text = "购买时间: ${item.purchaseDate}%"
        holder.itemView.setOnClickListener {
//            val intent = Intent(it.context,DetailActivity::class.java).apply {
//                putExtra("type",1) // asset
//                putExtra("asset",item)
//            }
//            it.context.startActivity(intent)
            // todo dialog for asset
            val ad = Dialog(MainActivity.mMainContext!!).apply {
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setCancelable(false)
                setContentView(R.layout.dialog_asset)
                findViewById<TextView>(R.id.title).text = item.productName
                findViewById<TextView>(R.id.amount).text = "金额: ${item.amount}"
                findViewById<TextView>(R.id.due).text = "到期时间: ${item.purchaseDate}"
                findViewById<Button>(R.id.ok).setOnClickListener {
                    dismiss()
                }
            }.show()
        }
    }

    override fun getItemCount(): Int = items.size
}