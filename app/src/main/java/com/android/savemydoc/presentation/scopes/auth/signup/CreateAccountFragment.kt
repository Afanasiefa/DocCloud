package com.android.savemydoc.presentation.scopes.auth.signup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.android.savemydoc.databinding.FragmentCreateAccountBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class CreateAccountFragment : Fragment() {
    private lateinit var binding: FragmentCreateAccountBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var viewModel: CreateAccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        auth = Firebase.auth
        binding = FragmentCreateAccountBinding.inflate(inflater)
        setupView()

        return binding.root
    }

    private fun setupView(){
        binding.createNewAccountBtn.setOnClickListener { createUserAccount(binding.emailEt.text.toString(), binding.passwordEt.text.toString()) }
    }

    private fun createUserAccount(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "createUserWithEmailAndPassword:success")
                    showHomeScreen()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "createUserWithEmailAndPassword:failure", task.exception)
                    Toast.makeText(
                        context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun showHomeScreen(){
        findNavController().navigate(CreateAccountFragmentDirections.actionCreateAccountFragmentToActionHome())
    }

}