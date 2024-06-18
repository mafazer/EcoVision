package com.example.ecovision.adapter

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecovision.R
import com.example.ecovision.data.local.HistoryEntity
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

class HistoryAdapter(
    private var historyList: List<HistoryEntity>,
    private val onDeleteHistory: (HistoryEntity) -> Unit,
    private val onChangeDescription: (HistoryEntity, String) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

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
        private val ivOption: ImageView = itemView.findViewById(R.id.ivOptions) // Add this line

        fun bind(historyItem: HistoryEntity) {
            CoroutineScope(Dispatchers.IO).launch {
                val inputDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                val outputDateFormat = SimpleDateFormat("dd MMMM\nyyyy", Locale.getDefault())
                val date = inputDateFormat.parse(historyItem.date)
                val formattedDate = date?.let { outputDateFormat.format(it) } ?: historyItem.date

                CoroutineScope(Dispatchers.Main).launch {
                    tvDate.text = formattedDate
                }
            }

            tvDescription.text = historyItem.description
            tvCategory.text = historyItem.category

            Glide.with(itemView.context)
                .load(historyItem.imageUri)
                .into(ivPhoto)

            ivOption.setOnClickListener {
                // Show a bottom sheet or dialog with options
                showOptionsDialog(historyItem)
            }
        }

        private fun showOptionsDialog(historyItem: HistoryEntity) {
            // Create and show a dialog with options (Delete and Change Description)
            val optionsDialog = BottomSheetDialog(itemView.context)
            val dialogView = LayoutInflater.from(itemView.context).inflate(R.layout.bottom_sheet_history, null)
            optionsDialog.setContentView(dialogView)

            dialogView.findViewById<TextView>(R.id.btnDeleteHistory).setOnClickListener {
                onDeleteHistory(historyItem)
                optionsDialog.dismiss()
            }

            dialogView.findViewById<TextView>(R.id.btnChangeDescription).setOnClickListener {
                showChangeDescriptionDialog(historyItem)
                optionsDialog.dismiss()
            }

            optionsDialog.show()
        }

        private fun showChangeDescriptionDialog(historyItem: HistoryEntity) {
            val context = itemView.context
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Change Description")

            val input = LayoutInflater.from(context).inflate(R.layout.dialog_change_description, null)
            builder.setView(input)

            builder.setPositiveButton("OK") { dialog, _ ->
                val newDescription = input.findViewById<TextView>(R.id.editDescription).text.toString()
                onChangeDescription(historyItem, newDescription)
                dialog.dismiss()
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }

            builder.show()
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