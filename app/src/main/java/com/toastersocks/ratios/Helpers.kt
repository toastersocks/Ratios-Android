package com.toastersocks.ratios

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * Created by James Pamplona on 3/7/18.
 */

fun EditText.afterTextChange(cb: (String) -> Unit) {
    this.addTextChangedListener(object: TextWatcher {
        override fun afterTextChanged(s: Editable?) { cb(s.toString()) }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}