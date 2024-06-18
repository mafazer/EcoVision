package com.example.ecovision.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecovision.R
import com.example.ecovision.adapter.HistoryAdapter
import com.example.ecovision.data.TipsData
import com.example.ecovision.data.local.HistoryRepository
import com.example.ecovision.databinding.FragmentHomeBinding
import com.example.ecovision.ui.GuideActivity
import com.example.ecovision.ui.MapsActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private lateinit var tipsData: TipsData
    private lateinit var auth: FirebaseAuth
    private lateinit var historyRepository: HistoryRepository
    private lateinit var historyAdapter: HistoryAdapter

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        val firebaseUser = auth.currentUser
        val displayName = firebaseUser?.displayName ?: "User"

        binding.textViewTitle.text = "Halo,\n$displayName"

        binding.imageViewQuestionMark.setOnClickListener {
            val intent = Intent(requireContext(), GuideActivity::class.java)
            startActivity(intent)
        }

        tipsData = TipsData()
        val tipOfTheDay = tipsData.getTipForToday()
        binding.textViewTip.text = tipOfTheDay

        historyRepository = HistoryRepository(requireContext())

        val recyclerView = binding.rvRecentHistory
        recyclerView.layoutManager = LinearLayoutManager(context)
        historyAdapter = HistoryAdapter(emptyList())
        recyclerView.adapter = historyAdapter

        lifecycleScope.launch(Dispatchers.IO) {
            val historyList = historyRepository.getLimitedHistoryItems(3) // Batasi 3 item
            val totalScans = historyRepository.getAllHistoryItems().size // Jumlah total pemindaian
            withContext(Dispatchers.Main) {
                historyAdapter.updateData(historyList)
                updateScanCount(totalScans)
            }
        }
    }

    private fun updateScanCount(totalScans: Int) {
        binding.textViewProgress.text = if (totalScans > 0) {
            "Mantapp, total kamu sudah $totalScans kali melakukan pemindaian sampah plastik!"
        } else {
            "Kamu belum nyoba fiturnya nih, ayo mulai pemindaian pertamamu!"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}