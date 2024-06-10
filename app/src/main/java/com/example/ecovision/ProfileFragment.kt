package com.example.ecovision

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import de.hdodenhof.circleimageview.CircleImageView

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Initialize views
        val profilePicture = view.findViewById<CircleImageView>(R.id.profilePicture)
        val username = view.findViewById<TextView>(R.id.username)
        val fullName = view.findViewById<TextView>(R.id.fullName)
        val email = view.findViewById<TextView>(R.id.email)
        val birthday = view.findViewById<TextView>(R.id.birthday)
        val location = view.findViewById<TextView>(R.id.location)
        val editButton = view.findViewById<Button>(R.id.editButton)
        val logoutButton = view.findViewById<Button>(R.id.logoutButton)

        // Set initial data
        username.text = "Rudytabootie"
        email.text = "rudytabootie@gmail.com"
        fullName.text = "not set"
        birthday.text = "not set"
        location.text = "not set"

        // Set click listeners for buttons if needed
        editButton.setOnClickListener {
            val intent = Intent(context, EditProfileActivity::class.java)
            startActivity(intent)
        }

        logoutButton.setOnClickListener {
            // Handle logout
        }
    }
}