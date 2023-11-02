package com.example.zadanie.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.zadanie.api.DataRepository
import com.google.android.material.snackbar.Snackbar
import com.example.zadanie.R
import com.example.zadanie.viewModels.AuthViewModel
import com.example.zadanie.databinding.FragmentSignupBinding


class SignupFragment : Fragment(R.layout.fragment_signup) {
    private lateinit var viewModel: AuthViewModel
    private var binding: FragmentSignupBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(), object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AuthViewModel(DataRepository.getInstance()) as T
            }
        })[AuthViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignupBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
        }.also { bnd ->
            viewModel.registrationResult.observe(viewLifecycleOwner) {
                if (it.isEmpty()) {
                    requireView().findNavController().navigate(R.id.action_signup_feed)
                } else {
                    Snackbar.make(
                        bnd.submitButton,
                        it,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }

            bnd.submitButton.apply {
                setOnClickListener {
                    viewModel.registerUser(
                        bnd.editTextUsername.text.toString(),
                        bnd.editTextEmail.text.toString(),
                        bnd.editTextPassword.text.toString()
                    )
                }
            }
        }
    }

}