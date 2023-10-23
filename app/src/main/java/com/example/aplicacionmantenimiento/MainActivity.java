package com.example.aplicacionmantenimiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnagregarmantenimiento = findViewById(R.id.btnagregarmantenimiento);
        Button btnvermantenimiento = findViewById(R.id.btnvermantenimiento);
        btnagregarmantenimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Crear una intención para abrir la nueva actividad
                Intent intent = new Intent(MainActivity.this, agregar_mantenimiento.class);

                // Iniciar la nueva actividad
                startActivity(intent);
            }
        });
        btnvermantenimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, historial_mantenimientos.class);
                startActivity(intent);
            }
        });


    }
}