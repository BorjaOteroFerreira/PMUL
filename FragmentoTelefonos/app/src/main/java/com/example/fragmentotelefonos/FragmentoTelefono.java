package com.example.fragmentotelefonos;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;


public class FragmentoTelefono extends Fragment implements onTelefonoListener {

    private onTelefonoFragmentListener mListener;
    private onTelefonoAction mListenerAction;
    TextView textView ;
    Telefono telefono ;
    String numTelefono;
    ImageButton btnLlamar;
    ImageButton btnColgar;
    EditText etDestino;
    TextInputLayout textInputLayout ;

    public FragmentoTelefono() {}

    public void setListener(onTelefonoFragmentListener listener, String numTelefono) {
        mListener = listener;
        this.numTelefono = numTelefono;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = view.findViewById(R.id.textView);
        btnLlamar = view.findViewById(R.id.btnLlamar);
        btnColgar = view.findViewById(R.id.btnColgar);
        etDestino = view.findViewById(R.id.etDestino);
        textInputLayout = view.findViewById(R.id.inputLayout);
        MyTextWatcher tw = new MyTextWatcher(textInputLayout);
        etDestino.addTextChangedListener(tw);
        btnLlamar.setOnClickListener(v -> {
                                            if(etDestino.getText().toString().isEmpty()){
                                                textInputLayout.setError(getString(R.string.campo_vacio));
                                                return;
                                            }
                                            telefono.llamar(etDestino.getText().toString());
                                            });
        btnColgar.setOnClickListener(v -> telefono.colgar());

        if (mListener != null) {
            this.telefono = mListener.obtenerTelefono(numTelefono);
            String telefono = this.telefono.getTelefono();
            textView.setText(telefono);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_telefono, container, false);
    }

    @Override
    public void recibirLlamada(String telefonoOrigen) {
        etDestino.setEnabled(false);
        btnLlamar.setEnabled(false);
    }

    @Override
    public void colgarIn() {
        etDestino.setEnabled(true);
        btnLlamar.setEnabled(true);
    }
}