package com.example.locmart.presentation.sign_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.locmart.R
import com.example.locmart.databinding.FragmentSignUpBinding
import com.example.locmart.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel by viewModels<SignUpViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        subscribeToLiveData()

        binding.passwordLayout.isHintAnimationEnabled = false

    }

    private fun subscribeToLiveData() = with(binding) {
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->

            progress.isVisible = isLoading
            register.text = if (isLoading) null else getString(R.string.fragment_sign_up_register)
        }
        viewModel.events.observe(viewLifecycleOwner) { event ->
            when (event) {

                SignUpViewModel.Event.AlreadyRegistered -> toast(R.string.already_registered)
                SignUpViewModel.Event.ConnectionErorr -> toast(R.string.connection_error)
                SignUpViewModel.Event.Erorr -> toast(R.string.erorr)
                SignUpViewModel.Event.NavigateToHome -> toast(R.string.app_name)

            }
        }


    }

    private fun initUI() = with(binding) {
        register.setOnClickListener {
            viewModel.signUp(
                username.text.toString(), email.text.toString(), password.text.toString()
            )
        }
        signIn.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.toSignInFragment())
        }
    }
}