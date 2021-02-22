package com.fdev.lovemusic.presentation.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fdev.lovemusic.R
import com.fdev.lovemusic.databinding.FragmentLoginBinding
import com.fdev.lovemusic.interactors.errorHandler
import com.fdev.lovemusic.presentation.BaseViewModel
import com.fdev.lovemusic.presentation.auth.AuthBaseFragment
import com.fdev.lovemusic.presentation.auth.register.RegisterFragment
import com.fdev.lovemusic.util.UIInteraction
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : AuthBaseFragment() {


    companion object {
        const val GOOGLE_SIGN_IN = 12
    }

    private val loginViewModel : LoginViewModel by viewModels()
    private var _binding: FragmentLoginBinding? = null

    private val binding
        get() = _binding!!


    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        initGoogleSignin()
        initObserver()
    }

    override fun setBaseViewModel(): BaseViewModel {
        return loginViewModel
    }

    private fun initObserver() {
        loginViewModel.loginStateView.observe(viewLifecycleOwner, { currentStateView ->

            currentStateView.loginOrNullResult.get()?.let{ result ->
                val loggedInUser = result.user
                if(loggedInUser == null){
                    navToRegister()
                }else{
                    activityViewModel.setCurrentUser(loggedInUser)
                }
            }

        })


    }





    private fun navToRegister() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment , bundleOf(RegisterFragment.IDTOKEN_STRING_KEY to loginViewModel.currentIdToken))
    }


    private fun initListener() {
        binding.apply {
            googleBtn.setOnClickListener {
                val signInIntent = googleSignInClient.signInIntent
                startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
            }
            facebookBtn.setOnClickListener {
                activityViewModel.showUserInteraction(UIInteraction.ShowToast(
                        toastMessage = "This Feature will be release soon",
                        toasTime = Toast.LENGTH_SHORT
                ))
            }
            appleBtn.setOnClickListener {
                activityViewModel.showUserInteraction(UIInteraction.ShowToast(
                        toastMessage = "This Feature will be release soon",
                        toasTime = Toast.LENGTH_SHORT
                ))
            }
        }
    }


    private fun initGoogleSignin() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.what_do_you_mean))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GOOGLE_SIGN_IN -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    account.idToken?.let{ idToken ->
                        loginViewModel.loginOrNull(idToken)

                    }
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    errorHandler(e)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}