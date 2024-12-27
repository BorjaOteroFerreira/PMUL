package com.example.fragmentotelefonos;

import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputLayout;

public class MyTextWatcher implements TextWatcher {
        TextInputLayout textInputLayout;
        public MyTextWatcher(TextInputLayout textInputLayout) {
            this.textInputLayout = textInputLayout;
        }
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void afterTextChanged(Editable editable) {
            if(!editable.toString().isEmpty()) {
                textInputLayout.setError(null);
                textInputLayout.setErrorEnabled(false);
            }}
}
