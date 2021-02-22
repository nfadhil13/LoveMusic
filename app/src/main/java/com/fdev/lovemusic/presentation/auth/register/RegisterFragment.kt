package com.fdev.lovemusic.presentation.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.fdev.lovemusic.databinding.FragmentRegisterBinding
import com.fdev.lovemusic.datasource.network.NetworkErrorConst
import com.fdev.lovemusic.presentation.BaseViewModel
import com.fdev.lovemusic.presentation.auth.AuthBaseFragment
import com.fdev.lovemusic.util.SingleEvent
import com.fdev.lovemusic.util.UIInteraction
import com.fdev.lovemusic.util.customview.ErrorData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : AuthBaseFragment() {


    companion object {
        const val IDTOKEN_STRING_KEY = "com.fdev.lovemusic.presentation.auth.register.idtokenkey"
    }

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
        initListener()
        iniatlizeBundle()
        observeVM()
    }

    override fun setBaseViewModel(): BaseViewModel {
        return registerViewModel
    }

    private fun iniatlizeBundle() {
        val idToken = arguments?.getString(IDTOKEN_STRING_KEY) ?: return
        registerViewModel.setCurrentIdToken(idToken = idToken)
    }

    private fun initListener(){
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
                        registerViewModel.register(
                                username = usernameTextField.getCurrentText()
                        )
                    },
                    onDisable = {
                        usernameTextField.requestError("Isi username")
                    }
            )
        }
    }


    private fun observeVM() {
        registerViewModel.registerViewState.observe(viewLifecycleOwner , { viewState ->
            viewState.registerResult.get()?.let{ result ->
                activityViewModel.setCurrentUser(result.user)
            }
        })
    }

    override fun handleUserInteraction(uiInteraction: UIInteraction){
        when(uiInteraction)  {
            is UIInteraction.GenericMessage -> {
                genericMessageHadnler(message = uiInteraction.message)
            }
            else -> {
                super.handleUserInteraction(uiInteraction)
            }
        }
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