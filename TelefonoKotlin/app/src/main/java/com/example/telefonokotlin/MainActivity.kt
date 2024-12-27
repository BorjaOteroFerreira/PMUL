package com.example.telefonokotlin

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val btnLlamar: ImageButton? = null
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
            btnLlamar!!.setOnClickListener { v: View? ->
                if (etDestino.getText().toString().isEmpty()) {
                    textInputLayout.setError(getString(R.string.campo_vacio))
                    return@setOnClickListener
                }
                telefono.llamar(etDestino.getText().toString())
            }

        }
    }
}

