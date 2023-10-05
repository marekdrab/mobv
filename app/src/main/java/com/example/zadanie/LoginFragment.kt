package com.example.zadanie

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

class LoginFragment: Fragment(R.layout.fragment_login) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Kód z Activity
        val submitButton: Button = view.findViewById(R.id.submitButton)
        submitButton.setOnClickListener {
            // Logika po kliknutí na tlačidlo, napríklad na získanie textu z EditText
            val input1: String = view.findViewById<EditText>(R.id.editText1).text.toString()
            val input2: String = view.findViewById<EditText>(R.id.editText2).text.toString()
            Log.d("inputs", "$input1, $input2")

            // Spracovanie dát alebo iné akcie
        }

        val registerButton: Button = view.findViewById(R.id.registerButton)
        registerButton.setOnClickListener {
            it.findNavController().navigate(R.id.loginToRegister)
        }

    }
}