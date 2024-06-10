package com.example.ecovision

import android.app.DatePickerDialog
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import de.hdodenhof.circleimageview.CircleImageView
import java.util.Calendar
import android.Manifest
import android.os.Build
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar

class EditProfileActivity : AppCompatActivity() {

    private lateinit var profilePicture: CircleImageView

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            profilePicture.setImageURI(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Edit Profile"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize views
        profilePicture = findViewById(R.id.profilePictureEdit)
        val changePictureButton = findViewById<Button>(R.id.changePictureButton)
        val fullNameEdit = findViewById<EditText>(R.id.fullNameEdit)
        val emailEdit = findViewById<EditText>(R.id.emailEdit)
        val birthdayEdit = findViewById<EditText>(R.id.birthdayEdit)
        val locationEdit = findViewById<EditText>(R.id.locationEdit)
        val saveButton = findViewById<Button>(R.id.saveButton)

        // Set initial data
        // Example data, this should be fetched from your data source
        fullNameEdit.setText("")
        emailEdit.setText("rudytabootie@gmail.com")
        birthdayEdit.setText("")
        locationEdit.setText("")

        // Handle change picture button click
        changePictureButton.setOnClickListener {
            checkPermission()
        }

        // Handle birthday edit text click to show date picker
        birthdayEdit.setOnClickListener {
            showDatePickerDialog(birthdayEdit)
        }

        // Handle save button click
        saveButton.setOnClickListener {
            // Handle save profile
            Toast.makeText(this, "Profile Saved", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()  // This will navigate back to the previous fragment
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openGallery() {
        pickImage.launch("image/*")
    }

    private fun showDatePickerDialog(editText: EditText) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
            editText.setText("${selectedDay}/${selectedMonth + 1}/${selectedYear}")
        }, year, month, day)

        datePickerDialog.show()
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {  // Android 13 and above
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_MEDIA_IMAGES), REQUEST_CODE_STORAGE_PERMISSION)
            } else {
                openGallery()
            }
        } else {  // Below Android 13
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE_STORAGE_PERMISSION)
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

    companion object {
        private const val REQUEST_CODE_STORAGE_PERMISSION = 1
    }
}