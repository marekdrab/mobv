package com.example.zadanie.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.zadanie.R
import com.example.zadanie.databinding.FragmentForgottenPasswordResetBinding

class ForgottenPasswordResetFragment: Fragment(R.layout.fragment_forgotten_password_reset) {
    private var binding: FragmentForgottenPasswordResetBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentForgottenPasswordResetBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
        }.also { bnd ->
            bnd.submitButton.apply {
                setOnClickListener {
                    it.findNavController().navigate(R.id.action_pw_reset_to_login)
                }
            }
        }
    }
}