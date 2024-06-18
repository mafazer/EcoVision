package com.example.ecovision.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecovision.R
import com.example.ecovision.data.local.HistoryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class HistoryAdapter(private var historyList: List<HistoryEntity>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    fun updateData(newHistoryList: List<HistoryEntity>) {
        val diffCallback = HistoryDiffCallback(historyList, newHistoryList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        historyList = newHistoryList
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history_detection, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val historyItem = historyList[position]
        holder.bind(historyItem)
    }

    override fun getItemCount(): Int = historyList.size

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        private val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        private val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        private val ivPhoto: ImageView = itemView.findViewById(R.id.ivPhoto)

        fun bind(historyItem: HistoryEntity) {
            // Format the date on a background thread
            CoroutineScope(Dispatchers.Default).launch {
                val inputDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                val outputDateFormat = SimpleDateFormat("dd MMMM\nyyyy", Locale.getDefault())
                val date = inputDateFormat.parse(historyItem.date)
                val formattedDate = date?.let { outputDateFormat.format(it) } ?: historyItem.date

                // Update UI on the main thread
                CoroutineScope(Dispatchers.Main).launch {
                    tvDate.text = formattedDate
                }
            }

            tvDescription.text = historyItem.description
            tvCategory.text = historyItem.category

            // Load image using Glide or Picasso on a background thread
            Glide.with(itemView.context)
                .load(historyItem.imageUri)
                .into(ivPhoto)
        }
    }
}

class HistoryDiffCallback(
    private val oldList: List<HistoryEntity>,
    private val newList: List<HistoryEntity>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}