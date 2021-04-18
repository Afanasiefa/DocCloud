package com.android.savemydoc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.android.savemydoc.databinding.ActivityMainBinding
import com.android.savemydoc.presentation.utils.StartFragmentDirections
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private var host: NavHostFragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        auth = Firebase.auth
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        host = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        host?.let { NavigationUI.setupWithNavController(binding.bottomMenu, it.navController) }
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            host?.findNavController()
                ?.navigate(StartFragmentDirections.actionStartFragmentToHomeFragment())
        } else {
            host?.findNavController()
                ?.navigate(StartFragmentDirections.actionStartFragmentToAuthFragment())
        }
    }
}