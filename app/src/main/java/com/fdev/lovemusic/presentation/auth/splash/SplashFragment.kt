package com.fdev.lovemusic.presentation.auth.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fdev.lovemusic.R
import com.fdev.lovemusic.databinding.FragmentSplashBinding
import com.fdev.lovemusic.presentation.BaseViewModel
import com.fdev.lovemusic.presentation.auth.AuthBaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : AuthBaseFragment() {


    private var _binding : FragmentSplashBinding? = null
    private val splashViewModel : SplashViewModel by viewModels()

    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        println("splash fragment creat")
        _binding = FragmentSplashBinding.inflate(inflater, container , false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeVM()
        splashViewModel.checkLastLoggedInUser()
    }

    override fun setBaseViewModel(): BaseViewModel {
        return splashViewModel
    }

    private fun observeVM() {
        splashViewModel.splashViewState.observe(viewLifecycleOwner , { viewState->

            viewState.checkLastLoggedInResult.get()?.let {  result ->
                val user = result.user
                if(user == null){
                    navToLoginFragment()
                }else{
                    activityViewModel.setCurrentUser(user)
                }
            }

        })
    }

    private fun navToLoginFragment(){
        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}