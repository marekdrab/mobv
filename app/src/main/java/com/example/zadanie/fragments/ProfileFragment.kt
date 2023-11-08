package com.example.zadanie.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.zadanie.R
import com.example.zadanie.api.DataRepository
import com.example.zadanie.data.PreferenceData
import com.example.zadanie.widgets.BottomBar
import com.example.zadanie.databinding.FragmentProfileBinding
import com.example.zadanie.viewModels.AuthViewModel
import com.example.zadanie.viewModels.ProfileViewModel
import com.google.android.material.snackbar.Snackbar

class ProfileFragment: Fragment(R.layout.fragment_profile) {
    private lateinit var viewModel: ProfileViewModel
    private var binding: FragmentProfileBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(), object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ProfileViewModel(DataRepository.getInstance()) as T
            }
        })[ProfileViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProfileBinding.bind(view).apply {
            lifecycleOwner = viewLifecycleOwner
            model = viewModel
        }.also { bnd ->
            bnd.bottomBar.setActive(BottomBar.PROFILE)
            bnd.loadProfileBtn.setOnClickListener {
                val user = PreferenceData.getInstance().getUser(requireContext())
                user?.let {
                    viewModel.loadUser(it.id, it.id, it.access, it.refresh)
                }
            }

            bnd.forgottenPw.setOnClickListener {
                    it.findNavController().navigate(R.id.action_change_pw)
            }

            bnd.logoutBtn.setOnClickListener {
                PreferenceData.getInstance().clearData(requireContext())
                it.findNavController().navigate(R.id.action_profile_intro)
            }

            viewModel.profileResult.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    Snackbar.make(
                        bnd.loadProfileBtn,
                        it,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }

        viewModel.userResult.observe(viewLifecycleOwner) {
            it?.let { profile ->
                PreferenceData.getInstance().putUser(requireContext(), profile)
            }
        }
    }
}