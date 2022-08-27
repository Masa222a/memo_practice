package com.android.example.memo

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MemoListAdapter: RecyclerView.Adapter<MemoListAdapter.MemoViewHolder>() {
    private val memoList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_memo, parent, false)
        return MemoViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemoViewHolder, position: Int) {
        holder.bind(memoList[position])
    }

    override fun getItemCount(): Int = memoList.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateMemoList(memoList: List<String>) {
        this.memoList.clear()
        this.memoList.addAll(memoList)
        notifyDataSetChanged()
    }

    class MemoViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(memo: String) {
            val textView = itemView.findViewById<TextView>(R.id.memo_text_view)
            textView.text = memo
        }
    }
}