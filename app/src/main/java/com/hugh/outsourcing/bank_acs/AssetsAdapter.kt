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

class AssetsAdapter(var items:List<Asset>):
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
        holder.desc.text = "购买时间: ${item.purchaseDate}"
        holder.itemView.setOnClickListener {
            // todo dialog for asset
            val ctx = MainActivity.mMainContext!!
            AssetDialog(ctx,item.productName,
                ctx.resources.getString(R.string.dialog_amount,item.amount),
                ctx.resources.getString(R.string.dialog_due,item.purchaseDate)
            ).show()
        }
    }

    override fun getItemCount(): Int = items.size
}