package com.toastersocks.ratios

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import java.io.Serializable

/**
 * Created by James Pamplona on 3/24/18.
 */

typealias DialogButtonOnClickListener = (dialog: DialogInterface, whichButton: Int) -> Unit

data class DialogButton(var title: String = "", var onClickListener: DialogButtonOnClickListener? = null): Serializable

class ErrorDialogFragment(): DialogFragment() {

    companion object {

        private val ARG_TITLE = "title"
        private val ARG_MESSAGE = "message"
        private val ARG_POSITIVE_BUTTON = "positiveButton"
        private val ARG_NEUTRAL_BUTTON = "neutralButton"
        private val ARG_NEGATIVE_BUTTON = "negativeButton"

        fun newInstance(
                title: String = "",
                message: String = "",
                positiveButton: DialogButton? = null,
                neutralButton: DialogButton? = null,
                negativeButton: DialogButton? = null): ErrorDialogFragment {

            val args = Bundle()
            args.putSerializable(ARG_TITLE, title)
            args.putSerializable(ARG_MESSAGE, message)
            args.putSerializable(ARG_POSITIVE_BUTTON, positiveButton)
            args.putSerializable(ARG_NEUTRAL_BUTTON, neutralButton)
            args.putSerializable(ARG_NEGATIVE_BUTTON, negativeButton)

            val fragment = ErrorDialogFragment()
            fragment.arguments = args

            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        var dialog =  super.onCreateDialog(savedInstanceState)


        val builder = AlertDialog.Builder(activity)

        with(builder) {

            arguments?.let {

                it.getString(ARG_TITLE)?.let { setTitle(it) }
                it.getString(ARG_MESSAGE)?.let { setMessage(it) }
                it.get(ARG_POSITIVE_BUTTON)?.let {
                    if (it is DialogButton) {
                        setPositiveButton(it.title, it.onClickListener)
                    }
                }

                it.get(ARG_NEUTRAL_BUTTON)?.let {
                    if (it is DialogButton) {
                        setNeutralButton(it.title, it.onClickListener)
                    }
                }
                it.get(ARG_NEGATIVE_BUTTON)?.let {
                    if (it is DialogButton) {
                        setNegativeButton(it.title, it.onClickListener)
                    }
                }
            }
        }

        return builder.create()
    }
}