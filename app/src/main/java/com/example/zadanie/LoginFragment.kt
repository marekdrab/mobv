package com.example.zadanie

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

class LoginFragment : Fragment(R.layout.fragment_login) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.submit_button).apply {
            setOnClickListener {
                val username: String =
                    view.findViewById<EditText>(R.id.edit_text_username).text.toString()
                val password: String =
                    view.findViewById<EditText>(R.id.edit_text_password).text.toString()
                login(username, password)
            }
        }

        view.findViewById<Button>(R.id.forgotten_pw).apply {
            setOnClickListener {
                it.findNavController().navigate(R.id.action_to_forgotten_pw)
            }
        }

    }

    private fun login(username: String, password: String) {
        findNavController().navigate(R.id.action_login_feed)
    }
}