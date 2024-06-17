package com.example.ecovision.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ecovision.R
import com.example.ecovision.databinding.ActivityMainBinding
import com.example.ecovision.ui.fragment.ArticleFragment
import com.example.ecovision.ui.fragment.HistoryFragment
import com.example.ecovision.ui.fragment.HomeFragment
import com.example.ecovision.ui.fragment.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra("username")
        if (username != null && username.isNotEmpty()) {
            Toast.makeText(this, "Welcome $username", Toast.LENGTH_SHORT).show()
        }

        binding.bottomNavigationView.menu.getItem(2).isEnabled = false

        val homeFragment = HomeFragment()
        val articleFragment = ArticleFragment()
        val historyFragment = HistoryFragment()
        val profileFragment = ProfileFragment()

        setCurrentFragment(homeFragment)

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.miHome -> setCurrentFragment(homeFragment)
                R.id.miArticle -> setCurrentFragment(articleFragment)
                R.id.miHistory -> setCurrentFragment(historyFragment)
                R.id.miProfile -> setCurrentFragment(profileFragment)
            }
            true
        }

        binding.fabScan.setOnClickListener {
            val intent = Intent(this, ScanActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameFragment, fragment)
            commit()
        }
}