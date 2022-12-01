package dev.cnemo.personalaccounting.textwatchers

import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class TextNotEmptyTextWatcher(val view: TextView, val submit: Button?): TextWatcher {
    private var lastestError = ""
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (view.isFocused){
            if (p0.isNullOrBlank()){
                view.error = "El campo no puede estar vacio"
            } else {
                view.error = null
            }
            if (view.error.isNullOrBlank().not() && view.error.toString() != lastestError){
                if (submit != null) {
                    submit.isClickable = false
                }
                Toast.makeText(view.context, view.error, Toast.LENGTH_SHORT).show()
                lastestError = view.error.toString()
            }
        }
    }

    override fun afterTextChanged(p0: Editable?) {

    }
}