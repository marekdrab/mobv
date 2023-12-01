package com.example.zadanie.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.zadanie.R
import com.example.zadanie.api.DataRepository
import com.example.zadanie.data.PreferenceData
import com.example.zadanie.databinding.FragmentChangePasswordBinding
import com.example.zadanie.databinding.FragmentLoginBinding
import com.example.zadanie.viewModels.AuthViewModel
import com.google.android.material.snackbar.Snackbar

class ChangePasswordFragment : Fragment(R.layout.fragment_change_password) {
    private lateinit var viewModel: AuthViewModel
    private var binding: FragmentChangePasswordBinding? = null

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

        binding = FragmentChangePasswordBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewModel
        }

        binding?.submitButton?.setOnClickListener {
            val oldPassword = binding?.oldPw?.text.toString()
            val newPassword = binding?.newPw?.text.toString()
            val repeatNewPassword = binding?.newPwRepeat?.text.toString()

            if (newPassword == repeatNewPassword) {
                viewModel.changePassword(oldPassword, newPassword)
            } else {
                binding?.newPwRepeat?.error = "Passwords don't match up"
            }

            viewModel.changePasswordResult.observe(viewLifecycleOwner) { result ->
                if (result == "true") {
                    it.findNavController().navigate(R.id.action_change_pw_profile)
                }
            }

            viewModel.changePasswordResultMessage.observe(viewLifecycleOwner) { result ->
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