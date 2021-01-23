package com.fdev.lovemusic.presentation.auth.login

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.fdev.lovemusic.R
import com.fdev.lovemusic.databinding.FragmentLoginBinding
import com.fdev.lovemusic.datasource.network.business.abstraction.UserNetworkDatasource
import com.fdev.lovemusic.datasource.network.business.implementation.UserNetworkDatasourceImpl
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class LoginFragment : Fragment() {


    private val loginViewModel : LoginViewModel by viewModels()

    companion object {
        const val GOOGLE_SIGN_IN = 12
    }

    private var _binding: FragmentLoginBinding? = null

    private val binding
        get() = _binding!!


    @Inject lateinit var  userNetworkDatasource: UserNetworkDatasource

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
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
        initAllSigninMethod()
        initObserver()
    }

    private fun initObserver() {
        loginViewModel.isUserExist.observe(viewLifecycleOwner , { isUserExist ->
            if(isUserExist){
                navToRegister()
            }
//            else{
//                //TODO
//                //Login
//                //Tell auth activity main activity
//            }
        })

        loginViewModel.errorMessage.observe(viewLifecycleOwner , {
            println("Error $it")
        })

        loginViewModel.loading.observe(viewLifecycleOwner , {
            toogleLoading(it)
        })
    }

    private fun toogleLoading(isLoading : Boolean){
        binding.apply {
            if(isLoading){
                overlay.visibility = View.VISIBLE
                progressBar.visibility = View.VISIBLE
            }else{
                overlay.visibility = View.GONE
                progressBar.visibility = View.GONE
            }
        }

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

    private fun initAllSigninMethod() {
        initGoogleSignin()
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
                    println("Error $e")
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}