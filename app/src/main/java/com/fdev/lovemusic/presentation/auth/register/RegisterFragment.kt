package com.fdev.lovemusic.presentation.auth.register

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.fdev.lovemusic.databinding.FragmentLoginBinding
import com.fdev.lovemusic.databinding.FragmentRegisterBinding
import com.fdev.lovemusic.util.customview.ErrorData

class RegisterFragment : Fragment() {


    private var _binding : FragmentRegisterBinding? = null

    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container , false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            usernameTextField.addOnErrorListener(isError = { currentText ->
                binding.daftarBtn.disableButton()
                if(currentText.isBlank() || currentText.isEmpty()){
                    return@addOnErrorListener  ErrorData.Error(message = "Username tidak boleh kosong")
                }
                if(currentText.contains(" ")){
                    return@addOnErrorListener ErrorData.Error(message =  "Tidak boleh ada spasi pada username")
                }
                if(currentText.length > 15){
                    return@addOnErrorListener ErrorData.Error(message =  "Maksimal 15 karakter")
                }
                binding.daftarBtn.enableButton()
                return@addOnErrorListener ErrorData.NotError
            })

            daftarBtn.setOnTrickyClickListener(
                    onEnable =  {
                        Toast.makeText(requireContext() , "Bisa" , Toast.LENGTH_SHORT).show()
                    },
                    onDisable = {
                        usernameTextField.requestError("Isi username")
                    }
            )
      }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding.usernameTextField.disposeView()
        _binding = null
    }


}