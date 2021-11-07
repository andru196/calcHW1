package com.example.calchw1.domain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calchw1.databinding.HistoryItemBinding
import com.example.calchw1.domain.entity.HistoryItem

class HistoryAdapter(
    private val onItemClicked: (HistoryItem) -> Unit
) : RecyclerView.Adapter<HistoryViewHolder>() {
    private var data : List<HistoryItem> = emptyList()

    fun setData(data: List<HistoryItem>){
        this.data = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            HistoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = data[position]
        with(holder.bindings) {
            expression.text = item.expression
            result.text = item.result
            root.setOnClickListener {
                onItemClicked(item)
            }
        }
    }

    override fun getItemCount(): Int = data.size

}

class HistoryViewHolder(val bindings:HistoryItemBinding): RecyclerView.ViewHolder(bindings.root)