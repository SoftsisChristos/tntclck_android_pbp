package com.example.tentoclock.general;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public abstract class  Utils {
    public static boolean isEditTextBlank(EditText editText) {
        if(editText.getText().toString().isBlank()) {
            editText.requestFocus();
            editText.setError("Υποχρεωτικό πεδίο");
            return true;
        }
        return false;
    }
}
