package com.fdev.lovemusic.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.fdev.lovemusic.util.SingleEvent
import com.fdev.lovemusic.util.UIInteraction

abstract class BaseFragment : Fragment() {


    private lateinit var activityBaseViewModel: ActivityBaseViewModel
    private lateinit var baseViewModel: BaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBaseViewModel()
        observeBaseVM()
    }



    abstract fun setActivityBaseViewModel() : ActivityBaseViewModel
    abstract fun setBaseViewModel() : BaseViewModel

    private fun initBaseViewModel() {
        activityBaseViewModel = setActivityBaseViewModel()
        baseViewModel = setBaseViewModel()
    }

    private fun observeBaseVM(){
        baseViewModel.userInteraction.observe(viewLifecycleOwner , { uiInterationEvent ->
            val uiInteraction = uiInterationEvent.get() ?: return@observe
            handleUserInteraction(uiInteraction)
        })

        baseViewModel.loading.observe(viewLifecycleOwner , { isShowLoading ->
            handleLoading(isShowLoading)
        })
    }

    protected open fun handleLoading(isShowLoading : Boolean){
        if(isShowLoading){
            activityBaseViewModel.startLoading()
        }else{
            activityBaseViewModel.finishLoading()
        }
    }

    protected open fun handleUserInteraction(uiInteraction: UIInteraction){
        activityBaseViewModel.showUserInteraction(uiInteraction)
    }


}