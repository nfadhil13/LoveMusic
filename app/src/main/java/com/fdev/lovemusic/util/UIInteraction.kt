package com.fdev.lovemusic.util

import android.widget.Toast

sealed class UIInteraction(val message: String) {

    object DoNothing : UIInteraction("")

    class GenericMessage(message : String) : UIInteraction(message)

    class ShowToast(toastMessage: String,var toasTime: Int = Toast.LENGTH_SHORT) : UIInteraction(toastMessage) {
        init {
            if (toasTime != 0 && toasTime != 1) {
                this.toasTime = 0
            }
        }
    }

//    class   Dialog(val dialogMessage : String) : ErrorEvent(dialogMessage)


}