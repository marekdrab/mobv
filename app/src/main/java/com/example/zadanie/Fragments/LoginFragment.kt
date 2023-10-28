package com.example.zadanie.Fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.zadanie.Api.DataRepository
import com.example.zadanie.R
import com.example.zadanie.ViewModels.AuthViewModel
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment(R.layout.fragment_login) {
    private lateinit var viewModel: AuthViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(), object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AuthViewModel(DataRepository.getInstance()) as T
            }
        })[AuthViewModel::class.java]

        view.findViewById<Button>(R.id.submit_button).apply {
            setOnClickListener {
                val username: String =
                    view.findViewById<EditText>(R.id.edit_text_username).text.toString()
                val password: String =
                    view.findViewById<EditText>(R.id.edit_text_password).text.toString()
                login(username, password)
            }
        }

        viewModel.loginResult.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                requireView().findNavController().navigate(R.id.action_login_feed)
            } else {
                Snackbar.make(
                    view.findViewById(R.id.submit_button),
                    it,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        view.findViewById<Button>(R.id.forgotten_pw).apply {
            setOnClickListener {
                it.findNavController().navigate(R.id.action_to_forgotten_pw)
            }
        }

    }

    private fun login(username: String, password: String) {
        viewModel.loginUser(username, password)
    }
}