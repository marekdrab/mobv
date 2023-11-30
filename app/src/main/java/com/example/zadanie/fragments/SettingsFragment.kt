package com.example.zadanie.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.zadanie.R
import com.example.zadanie.data.PreferenceData
import com.example.zadanie.databinding.FragmentSettingsBinding

class SettingsFragment: Fragment(R.layout.fragment_settings) {
    private var binding: FragmentSettingsBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSettingsBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
        }.also { bnd ->
            bnd.forgottenPw.apply {
                setOnClickListener {
                    it.findNavController().navigate(R.id.action_change_pw)
                }
            }
            bnd.logoutBtn.apply {
                setOnClickListener {
                    PreferenceData.getInstance().clearData(requireContext())
                    it.findNavController().navigate(R.id.action_profile_intro)
                }
            }
        }
    }
}