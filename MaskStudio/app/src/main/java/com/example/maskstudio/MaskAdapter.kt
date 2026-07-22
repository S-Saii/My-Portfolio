package com.example.maskstudio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MaskAdapter(
    private val masks: List<MaskItem>,
    private val onItemClick: (MaskItem) -> Unit
) : RecyclerView.Adapter<MaskAdapter.MaskViewHolder>() {

    class MaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvMaskName)
        val tvDesc: TextView = view.findViewById(R.id.tvMaskDesc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mask, parent, false)
        return MaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: MaskViewHolder, position: Int) {
        val mask = masks[position]
        holder.tvName.text = mask.name
        holder.tvDesc.text = mask.description
        holder.itemView.setOnClickListener { onItemClick(mask) }
    }

    override fun getItemCount(): Int = masks.size
}