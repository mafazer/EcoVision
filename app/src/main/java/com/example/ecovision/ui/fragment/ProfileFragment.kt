package com.example.ecovision.ui.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.ecovision.databinding.FragmentProfileBinding
import com.example.ecovision.login.LoginActivity
import com.example.ecovision.ui.EditProfileActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val REQUEST_CODE_EDIT_PROFILE = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()
        val firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            // Not signed in, launch the Login activity
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()
            return
        }

        // Set initial data
        updateProfileData()

        // Set click listeners for buttons if needed
        binding.editButton.setOnClickListener {
            val intent = Intent(context, EditProfileActivity::class.java).apply {
                putExtra("isFirebaseUser", true)
                putExtra("displayName", firebaseUser.displayName)
                putExtra("email", firebaseUser.email)
                putExtra("photoUrl", firebaseUser.photoUrl.toString())
                putExtra("fullName", binding.fullName.text.toString())
                putExtra("birthday", binding.birthday.text.toString())
                putExtra("location", binding.location.text.toString())
            }
            startActivityForResult(intent, REQUEST_CODE_EDIT_PROFILE)
        }

        binding.logoutButton.setOnClickListener {
            signOut()
        }
    }

    private fun updateProfileData() {
        val firebaseUser = auth.currentUser
        binding.username.text = firebaseUser?.displayName ?: "Username not set"
        binding.email.text = firebaseUser?.email ?: "Email not set"

        val userId = firebaseUser?.uid
        userId?.let {
            db.collection("users").document(it).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val fullName = document.getString("fullName") ?: "not set"
                        val birthday = document.getString("birthday") ?: "not set"
                        val location = document.getString("location") ?: "not set"
                        binding.fullName.text = fullName
                        binding.birthday.text = birthday
                        binding.location.text = location
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e("ProfileFragment", "Error getting profile data: ${exception.message}")
                }

            firebaseUser.photoUrl?.let { photoUrl ->
                Glide.with(this).load(photoUrl).into(binding.profilePicture)
            }
        }
    }

    private fun signOut() {
        binding.progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            val credentialManager = CredentialManager.create(requireContext())
            auth.signOut()
            credentialManager.clearCredentialState(ClearCredentialStateRequest())
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            activity?.finish()
        }.invokeOnCompletion {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_EDIT_PROFILE && resultCode == Activity.RESULT_OK) {
            // Refresh profile data
            updateProfileData()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
