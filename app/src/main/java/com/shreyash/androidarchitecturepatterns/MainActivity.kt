package com.shreyash.androidarchitecturepatterns

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.shreyash.androidarchitecturepatterns.databinding.ActivityMainBinding
import com.shreyash.mvc.MVCMainActivity
import com.shreyash.mvi.view.MVIMainActivity
import com.shreyash.mvp.MVPMainActivity
import com.shreyash.mvvm.view.MVVMMainActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inflate the binding and set the content view
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)  // Set the content view to the root view of the binding

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Now set up the click listeners
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnMvc.setOnClickListener {
            Log.i("TAG", "setupClickListeners: ")
            startActivity(Intent(this, MVCMainActivity::class.java))
        }

        binding.btnMvp.setOnClickListener {
            startActivity(Intent(this, MVPMainActivity::class.java))
        }

        binding.btnMvvm.setOnClickListener {
            startActivity(Intent(this, MVVMMainActivity::class.java))
        }

        binding.btnMvi.setOnClickListener {
            startActivity(Intent(this, MVIMainActivity::class.java))
        }
    }
}