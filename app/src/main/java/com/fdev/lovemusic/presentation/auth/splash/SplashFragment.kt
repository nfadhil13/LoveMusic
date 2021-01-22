package com.fdev.lovemusic.presentation.auth.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fdev.lovemusic.R
import com.fdev.lovemusic.databinding.FragmentRegisterBinding
import com.fdev.lovemusic.databinding.FragmentSplashBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main

class SplashFragment : Fragment() {


    private var _binding : FragmentSplashBinding? = null

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
        CoroutineScope(Dispatchers.IO).launch {
            delay(500)
            withContext(Main){
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}