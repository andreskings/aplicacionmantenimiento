package com.example.aplicacionmantenimiento;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button botonver = findViewById(R.id.btnvermantenimiento);
        Button botonagregar = findViewById(R.id.btnagregarmantenimiento);

        // Configura un OnClickListener para el ImageButton
        botonver.setOnClickListener(v -> {
            // Crear un Intent para iniciar la actividad DatosOtroActivity
            Intent intent = new Intent(MainActivity.this, historial_mantenimientos.class);
            startActivity(intent);
        });
        botonagregar.setOnClickListener(v -> {
            // Crear un Intent para iniciar la actividad DatosPerro
            Intent intent = new Intent(MainActivity.this, agregar_mantenimiento.class);
            startActivity(intent);
        });
    }
}
