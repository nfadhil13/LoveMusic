package com.fdev.lovemusic.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

abstract class BaseFragment : Fragment() {


    protected val interactorViewModel : InteractorViewModel by activityViewModels()

}