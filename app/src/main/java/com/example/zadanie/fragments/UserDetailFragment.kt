package com.example.zadanie.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.zadanie.R
import com.example.zadanie.database.entities.UserEntity
import com.squareup.picasso.Picasso
import com.example.zadanie.databinding.FragmentUserDetailBinding
import com.example.zadanie.viewModels.UserViewModel

class UserDetailFragment : Fragment(R.layout.fragment_user_detail) {
//    private val viewModel: UserViewModel
    private var binding: FragmentUserDetailBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        val id =arguments?.getString("userId")
        if (id != null) {
//            viewModel.getUser(id)
        }
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
