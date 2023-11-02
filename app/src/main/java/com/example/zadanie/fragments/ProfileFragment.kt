package com.example.zadanie.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.zadanie.R
import com.example.zadanie.widgets.BottomBar
import com.example.zadanie.databinding.FragmentProfileBinding

class ProfileFragment: Fragment(R.layout.fragment_profile) {
    private var binding: FragmentProfileBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProfileBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
        }.also { bnd ->
            bnd.bottomBar.setActive(BottomBar.PROFILE)
            bnd.forgottenPw.apply {
                setOnClickListener {
                    it.findNavController().navigate(R.id.action_change_pw)
                }
            }
        }
    }
}