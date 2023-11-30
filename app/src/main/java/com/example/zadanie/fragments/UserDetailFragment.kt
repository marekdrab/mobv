package com.example.zadanie.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zadanie.R
import com.example.zadanie.api.DataRepository
import com.example.zadanie.config.Config
import com.squareup.picasso.Picasso
import com.example.zadanie.databinding.FragmentUserDetailBinding
import com.example.zadanie.viewModels.ProfileViewModel

class UserDetailFragment : Fragment(R.layout.fragment_user_detail) {
    private lateinit var viewModel: ProfileViewModel
    private var binding: FragmentUserDetailBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(), object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ProfileViewModel(DataRepository.getInstance(requireContext())) as T
            }
        })[ProfileViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentUserDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_detail, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.model = viewModel
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getString("id")
        id?.let {
            viewModel.loadUser(it)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
