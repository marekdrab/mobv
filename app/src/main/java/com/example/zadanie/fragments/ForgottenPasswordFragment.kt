package com.example.zadanie.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.zadanie.R
import com.example.zadanie.api.DataRepository
import com.example.zadanie.databinding.FragmentForgottenPasswordBinding
import com.example.zadanie.viewModels.AuthViewModel
import com.google.android.material.snackbar.Snackbar

class ForgottenPasswordFragment : Fragment(R.layout.fragment_forgotten_password) {
    private var binding: FragmentForgottenPasswordBinding? = null
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(), object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AuthViewModel(DataRepository.getInstance(requireContext())) as T
            }
        })[AuthViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentForgottenPasswordBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
        }

        binding?.submitButton?.setOnClickListener {
            val email = binding?.editTextEmail?.text.toString()
            if (email.isNotEmpty()) {
                viewModel.resetPassword(email)
            } else {
                binding?.editTextEmail?.error = "Email is empty"
            }
            viewModel.resetPasswordResult.observe(viewLifecycleOwner) { result ->
                if (result == true) {
                    it.findNavController().navigate(R.id.action_pw_reset_to_login)
                }
            }

            viewModel.resetPasswordResultMessage.observe(viewLifecycleOwner) { result ->
                Log.d("test", result.toString())
                if (!result.isNullOrEmpty()) {
                    Snackbar.make(
                        view,
                        result,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}