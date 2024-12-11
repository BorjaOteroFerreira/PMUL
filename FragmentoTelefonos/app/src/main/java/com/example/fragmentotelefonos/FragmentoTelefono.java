package com.example.fragmentotelefonos;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FragmentoTelefono extends Fragment  {
    private onFragmentInteractionListener mListener;
    TextView textView ;
    int idDispositivo;
    public FragmentoTelefono() {}
    
    public void setListener(onFragmentInteractionListener listener, int idDispositivo) {
        mListener = listener;
        this.idDispositivo = idDispositivo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textView = view.findViewById(R.id.textView);

        if (mListener != null) {
            String telefono = mListener.obtenerTelefono(idDispositivo);
            textView.setText(telefono);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_telefono, container, false);
    }


}