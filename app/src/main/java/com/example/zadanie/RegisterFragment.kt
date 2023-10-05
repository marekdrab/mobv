package com.example.zadanie

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment

class RegisterFragment : Fragment(R.layout.fragment_register) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val submitButton: Button = view.findViewById(R.id.submitButton)
        submitButton.setOnClickListener {
            // Logika po kliknutí na tlačidlo, napríklad na získanie textu z EditText
            Log.d("inputs", "tag")
            // Spracovanie dát alebo iné akcie
        }
    }
}