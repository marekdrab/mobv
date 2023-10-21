package com.example.zadanie

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

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