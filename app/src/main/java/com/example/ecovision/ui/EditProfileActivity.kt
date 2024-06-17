package com.example.ecovision.ui

import android.app.DatePickerDialog
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.Calendar
import android.Manifest
import android.app.Activity
import android.os.Build
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import com.example.ecovision.R
import com.example.ecovision.databinding.ActivityEditProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            binding.profilePictureEdit.setImageURI(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Edit Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val isFirebaseUser = intent.getBooleanExtra("isFirebaseUser", false)
        val displayName = intent.getStringExtra("displayName") ?: "not set"
        val email = intent.getStringExtra("email")
        val photoUrl = intent.getStringExtra("photoUrl")
        val fullName = intent.getStringExtra("fullName")
        val birthday = intent.getStringExtra("birthday")
        val location = intent.getStringExtra("location")

        // Set initial data
        binding.fullNameEdit.setText(fullName)
        binding.emailEdit.setText(email)
        binding.birthdayEdit.setText(birthday)
        binding.locationEdit.setText(location)

        if (isFirebaseUser) {
            binding.changePictureButton.visibility = View.GONE
            Glide.with(this).load(photoUrl).into(binding.profilePictureEdit)
        } else {
            binding.changePictureButton.visibility = View.VISIBLE
            binding.profilePictureEdit.setImageResource(R.drawable.ic_profile) // Default profile picture
        }

        binding.changePictureButton.setOnClickListener {
            checkPermission()
        }

        binding.birthdayEdit.setOnClickListener {
            showDatePickerDialog()
        }

        binding.saveButton.setOnClickListener {
            saveProfile()
            Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show()
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openGallery() {
        pickImage.launch("image/*")
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            binding.birthdayEdit.setText("${selectedDay}/${selectedMonth + 1}/${selectedYear}")
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {  // Android 13 and above
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_MEDIA_IMAGES), REQUEST_CODE_STORAGE_PERMISSION
                )
            } else {
                openGallery()
            }
        } else {  // Below Android 13
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_STORAGE_PERMISSION
                )
            } else {
                openGallery()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveProfile() {
        val fullName = binding.fullNameEdit.text.toString()
        val birthday = binding.birthdayEdit.text.toString()
        val location = binding.locationEdit.text.toString()

        val user = auth.currentUser
        val userId = user?.uid

        if (userId != null) {
            val userProfile = hashMapOf(
                "fullName" to fullName,
                "birthday" to birthday,
                "location" to location
            )

            db.collection("users").document(userId)
                .set(userProfile)
                .addOnSuccessListener {
                    Log.d("EditProfileActivity", "Profile updated successfully")
                    Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                    setResult(Activity.RESULT_OK)
                    finish()
                }
                .addOnFailureListener { e ->
                    Log.e("EditProfileActivity", "Failed to update profile: ${e.message}")
                    Toast.makeText(this, "Failed to update profile: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Log.e("EditProfileActivity", "User ID is null")
            Toast.makeText(this, "Failed to update profile: User not logged in", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val REQUEST_CODE_STORAGE_PERMISSION = 1
    }
}
