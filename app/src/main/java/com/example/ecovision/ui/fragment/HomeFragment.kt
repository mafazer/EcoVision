package com.example.ecovision.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ecovision.data.TipsData
import com.example.ecovision.databinding.FragmentHomeBinding
import com.example.ecovision.ui.GuideActivity
import com.example.ecovision.ui.MapsActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {

    private lateinit var tipsData: TipsData
    private lateinit var auth: FirebaseAuth

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}