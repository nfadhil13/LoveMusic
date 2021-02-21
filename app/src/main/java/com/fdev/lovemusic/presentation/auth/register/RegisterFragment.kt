package com.fdev.lovemusic.presentation.auth.register

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.fdev.lovemusic.databinding.FragmentLoginBinding
import com.fdev.lovemusic.databinding.FragmentRegisterBinding
import com.fdev.lovemusic.datasource.network.NetworkErrorConst
import com.fdev.lovemusic.presentation.BaseFragment
import com.fdev.lovemusic.util.UIInteraction
import com.fdev.lovemusic.util.customview.ErrorData

class RegisterFragment : BaseFragment() {


    private var _binding: FragmentRegisterBinding? = null
    private val registerViewModel: RegisterViewModel by viewModels()

    private val binding
        get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            usernameTextField.addOnErrorListener(isError = { currentText ->
                binding.daftarBtn.disableButton()
                if (currentText.isBlank() || currentText.isEmpty()) {
                    return@addOnErrorListener ErrorData.Error(message = "Username tidak boleh kosong")
                }
                if (currentText.contains(" ")) {
                    return@addOnErrorListener ErrorData.Error(message = "Tidak boleh ada spasi pada username")
                }
                if (currentText.length > 15) {
                    return@addOnErrorListener ErrorData.Error(message = "Maksimal 15 karakter")
                }
                binding.daftarBtn.enableButton()
                return@addOnErrorListener ErrorData.NotError
            })

            daftarBtn.setOnTrickyClickListener(
                    onEnable = {
                        Toast.makeText(requireContext(), "Bisa", Toast.LENGTH_SHORT).show()
                    },
                    onDisable = {
                        usernameTextField.requestError("Isi username")
                    }
            )
        }
        observeVM()
    }


    private fun observeVM() {
        registerViewModel.userInteraction.observe(viewLifecycleOwner, {
            val userInteraction = it.get() ?: return@observe
            when (userInteraction) {
                is UIInteraction.GenericMessage -> {
                    genericMessageHadnler(userInteraction.message)
                }

                else -> {
                    interactorViewModel.showUserInteraction(userInteraction)
                }
            }

        })

        registerViewModel.loading.observe(viewLifecycleOwner, { isShowLoading ->
            if (isShowLoading) {
                interactorViewModel.startLoading()
            } else {
                interactorViewModel.finishLoading()
            }
        })

    }

    private fun genericMessageHadnler(message: String) {
        when(message){
            NetworkErrorConst.USER.USERNAME_EXIST -> {
                binding.usernameTextField.requestError(message)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.usernameTextField.disposeView()
        _binding = null
    }


}