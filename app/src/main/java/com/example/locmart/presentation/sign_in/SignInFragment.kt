package com.example.locmart.presentation.sign_in

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.locmart.R
import com.example.locmart.databinding.FragmentSignInBinding
import com.example.locmart.util.clearLightStatusBar
import com.example.locmart.util.toast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignInFragment :Fragment() {
    private lateinit var binding: FragmentSignInBinding
    private val viewModel by viewModels<SignInViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        subscribeToLiveData()

    }

    override fun onPause() {
        super.onPause()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }
    private fun subscribeToLiveData() = with(binding) {
        viewModel.loading.observe(viewLifecycleOwner){ isLoading ->

            progress.isVisible = isLoading
            signIn.text = if (isLoading) null else getString(R.string.fragment_sing_in_button)


        }
        viewModel.events.observe(viewLifecycleOwner){event ->
            when(event){
                SignInViewModel.Event.ConnectionErorr -> toast(R.string.connection_error)
                SignInViewModel.Event.Erorr -> toast(R.string.erorr)
                SignInViewModel.Event.InvalidCredentials -> toast(R.string.invalid_credentials)
                SignInViewModel.Event.NavigateToHome -> toast(R.string.app_name)
            }
        }
    }

    private fun initUI() = with(binding){
        clearLightStatusBar()
        signIn.setOnClickListener {
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()
            viewModel.signIn(username, password)
        }
        signUp.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.toSignUpFragment())
        }
    }

}