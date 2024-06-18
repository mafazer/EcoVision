package com.example.ecovision.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecovision.R
import com.example.ecovision.adapter.HistoryAdapter
import com.example.ecovision.data.local.HistoryEntity
import com.example.ecovision.data.local.HistoryRepository
import com.example.ecovision.databinding.FragmentHistoryBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryFragment : Fragment() {

    private lateinit var historyRepository: HistoryRepository
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyRepository = HistoryRepository(requireContext())

        binding.rvRecentHistory.layoutManager = LinearLayoutManager(context)
        historyAdapter = HistoryAdapter(emptyList(), ::onDeleteHistory, ::onChangeDescription)
        binding.rvRecentHistory.adapter = historyAdapter

        loadHistory()
    }

    override fun onResume() {
        super.onResume()
        loadHistory()
    }

    private fun loadHistory() {
        lifecycleScope.launch(Dispatchers.IO) {
            val historyList = historyRepository.getAllHistoryItems()
            withContext(Dispatchers.Main) {
                historyAdapter.updateData(historyList)
            }
        }
    }

    private fun onDeleteHistory(historyItem: HistoryEntity) {
        lifecycleScope.launch(Dispatchers.IO) {
            historyRepository.deleteHistoryItem(historyItem)
            val updatedList = historyRepository.getAllHistoryItems()
            withContext(Dispatchers.Main) {
                historyAdapter.updateData(updatedList)
            }
        }
    }

    private fun onChangeDescription(historyItem: HistoryEntity, newDescription: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            historyRepository.updateHistoryDescription(historyItem, newDescription)
            val updatedList = historyRepository.getAllHistoryItems()
            withContext(Dispatchers.Main) {
                historyAdapter.updateData(updatedList)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}