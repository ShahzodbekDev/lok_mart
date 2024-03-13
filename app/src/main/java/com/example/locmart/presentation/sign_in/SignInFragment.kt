package com.example.locmart.presentation.sign_in

import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.locmart.R
import com.example.locmart.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignInFragment :Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        subscribeToLiveData()

    }

    private fun subscribeToLiveData() = with(binding) {
        viewModel.loading.observe(viewLifecycleOwner){ isLoading ->

            progress.isVisible = isLoading

        }
        viewModel.events.observe(viewLifecycleOwner){event ->
            when(event){
                SignInViewModel.Event.ConnectionErorr -> toast(R.string.connection_error)
                SignInViewModel.Event.Erorr -> toast(R.string.erorr)
                SignInViewModel.Event.InvalidCredentials -> toast(R.string.invalid_credentials)
            }
        }
    }

    private fun initUI() = with(binding){
        signIn.setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()
            viewModel.signIn(username, password)
        }
    }

    private fun toast(message:Int){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}