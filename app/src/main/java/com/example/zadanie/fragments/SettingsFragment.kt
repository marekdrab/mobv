package com.example.zadanie.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.zadanie.R
import com.example.zadanie.api.DataRepository
import com.example.zadanie.data.PreferenceData
import com.example.zadanie.databinding.FragmentSettingsBinding
import com.example.zadanie.viewModels.AuthViewModel

class SettingsFragment: Fragment(R.layout.fragment_settings) {
    private var binding: FragmentSettingsBinding? = null
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(), object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AuthViewModel(DataRepository.getInstance(requireContext())) as T
            }
        })[AuthViewModel::class.java]
    }

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
                    viewModel.logoutUser()
                    it.findNavController().navigate(R.id.action_profile_intro)
                }
            }
        }
    }
}