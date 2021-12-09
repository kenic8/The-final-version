package com.example.ShoppingListExam

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment

//notice that the negClick has default value of the empty function
open class DialogFragment(var posClick: ()-> Unit, var negClick: ()->Unit= {}) : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val alert = AlertDialog.Builder(
                activity)
        alert.setTitle(R.string.confirmation)
        alert.setMessage(R.string.areYouSure)
        alert.setPositiveButton(R.string.yes, pListener)
        alert.setNegativeButton(R.string.no, nListener)

        return alert.create()
    }

    private var pListener: DialogInterface.OnClickListener = DialogInterface.OnClickListener {_, _ ->
        // these will be executed when user click Yes button
        posClick()
    }

    //This is our negative listener for when the user presses
    //the no button.
    private var nListener: DialogInterface.OnClickListener = DialogInterface.OnClickListener { _, _ ->
        // these will be executed when user click No button
        negClick()
    }

}