package com.fdev.lovemusic.util

import android.widget.Toast

sealed class UserInteraction(val message: String) {

    object DoNothing : UserInteraction("")

    class ShowToast(toastMessage: String,var toasTime: Int = Toast.LENGTH_SHORT) : UserInteraction(toastMessage) {
        init {
            if (toasTime != 0 && toasTime != 1) {
                this.toasTime = 0
            }
        }
    }

//    class   Dialog(val dialogMessage : String) : ErrorEvent(dialogMessage)


}