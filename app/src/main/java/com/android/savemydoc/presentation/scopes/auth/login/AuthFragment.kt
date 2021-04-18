package com.android.savemydoc.presentation.scopes.auth.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.savemydoc.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)

        auth = Firebase.auth

        binding.emailAndPassSignInBtn.setOnClickListener {
            startSignIn(
                binding.emailEt.text.toString(),
                binding.passwordEt.text.toString()
            )
        }

        binding.signUp.setOnClickListener {  findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToCreateAccountFragment()) }

        return binding.root
    }

    private fun startSignIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithEmail:success")
                    val user = auth.currentUser
                    showHomeScreen()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
//                    updateUI(null)
                }
            }
    }

    private fun showHomeScreen(){
        findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToHomeFragment())
    }
}