package com.fdev.lovemusic.presentation.auth

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.fdev.lovemusic.presentation.ActivityBaseViewModel
import com.fdev.lovemusic.presentation.BaseFragment

abstract class AuthBaseFragment : BaseFragment() {


    protected val activityViewModel : AuthActivityViewModel by activityViewModels()

    override fun setActivityBaseViewModel(): ActivityBaseViewModel = activityViewModel

}