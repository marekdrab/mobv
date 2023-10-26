package com.example.zadanie.Fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.zadanie.R

class ForgottenPasswordResetFragment: Fragment(R.layout.fragment_forgotten_password_reset) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.submit_button).apply {
            setOnClickListener {
                it.findNavController().navigate(R.id.action_pw_reset_to_login)
            }
        }
    }
}