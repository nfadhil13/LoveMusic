package com.fdev.lovemusic.presentation.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fdev.lovemusic.R
import com.fdev.lovemusic.databinding.FragmentLoginBinding
import com.fdev.lovemusic.interactors.errorHandler
import com.fdev.lovemusic.presentation.BaseFragment
import com.fdev.lovemusic.presentation.InteractorViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : BaseFragment() {


    private val loginViewModel : LoginViewModel by viewModels()


    companion object {
        const val GOOGLE_SIGN_IN = 12
    }

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

    private fun initObserver() {
        loginViewModel.isUserExist.observe(viewLifecycleOwner, { userExistSingleEvent ->
            val isUserExist = userExistSingleEvent.get() ?:  return@observe
            if(isUserExist){
                navToRegister()
            }else{
                //TODO : NAV TO MAIN ACTIVITY
            }
        })

        loginViewModel.userInteraction.observe(viewLifecycleOwner , {
            val userInteraction = it.get() ?: return@observe
            interactorViewModel.showUserInteraction(userInteraction)
        })

        loginViewModel.loading.observe(viewLifecycleOwner , { isShowLoading ->
            if(isShowLoading){
                interactorViewModel.startLoading()
            }else{
                interactorViewModel.finishLoading()
            }
        })
    }



    private fun navToRegister() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }


    private fun initListener() {
        binding.apply {
            googleBtn.setOnClickListener {
                val signInIntent = googleSignInClient.signInIntent
                startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
            }
            facebookBtn.setOnClickListener {
                Toast.makeText(requireContext() , "This Feature will be release soon" , Toast.LENGTH_SHORT).show()
            }
            appleBtn.setOnClickListener {
                Toast.makeText(requireContext() , "This Feature will be release soon" , Toast.LENGTH_SHORT).show()
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
                        loginViewModel.checkIfUserExist(idToken)

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