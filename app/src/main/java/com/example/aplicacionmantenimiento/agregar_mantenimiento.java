package com.example.aplicacionmantenimiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class agregar_mantenimiento extends AppCompatActivity {
    private EditText editTextTitulo;
    private EditText editTextDescripcion;
    private LinearLayout linearAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_mantenimiento);

        editTextTitulo = findViewById(R.id.titulomantenimiento);
        editTextDescripcion = findViewById(R.id.descripcion);
        linearAgregar = findViewById(R.id.lineragregar);

        Button btnGuardar = findViewById(R.id.btnguardarman);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarInformacionEnArchivo();
            }
        });

        Button btnLimpiar = findViewById(R.id.btnlimpiar);
        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiarDatos();
            }
        });

        Button btnConfigurarAlarma = findViewById(R.id.btnconfiguraralarma);
        btnConfigurarAlarma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(agregar_mantenimiento.this, crear_alarma.class);
                startActivity(intent);
            }
        });

        // Mostrar el contenido del archivo al iniciar la actividad
        mostrarContenidoDeArchivo();
    }

    private void guardarInformacionEnArchivo() {
        String titulo = editTextTitulo.getText().toString();
        String descripcion = editTextDescripcion.getText().toString();
        String informacion = "Título: " + titulo + "\nDescripción: " + descripcion;

        try {
            // Abre o crea el archivo en modo de apéndice (si no existe, lo crea)
            FileOutputStream fileOutputStream = openFileOutput("txtagregar.txt", MODE_APPEND);
            OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream);

            writer.write(informacion);
            writer.write("\n");

            writer.close();
            fileOutputStream.close();

            Toast.makeText(this, "Datos guardados correctamente", Toast.LENGTH_SHORT).show();

            // Crear una nueva vista con el diseño de activity_historial_mantenimientos
            View nuevoMantenimientoView = LayoutInflater.from(this).inflate(R.layout.activity_historial_mantenimientos, null);

            // Configurar los elementos de la vista
            TextView tituloTextView = nuevoMantenimientoView.findViewById(R.id.idTVMaintenanceTitle);
            TextView descripcionTextView = nuevoMantenimientoView.findViewById(R.id.idTVMaintenanceDescription);

            tituloTextView.setText("Registro mantenimiento " + titulo); // Establece el título
            descripcionTextView.setText(descripcion); // Establece la descripción

            // Agregar la vista creada dinámicamente al contenedor
            linearAgregar.addView(nuevoMantenimientoView);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al guardar los datos", Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarContenidoDeArchivo() {
        try {
            FileInputStream fileInputStream = openFileInput("txtagregar.txt");
            InputStreamReader reader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }

            reader.close();
            // Actualizar el contenido del elemento TextView
            // txtAgregar.setText(stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void limpiarDatos() {
        try {
            // Abre el archivo en modo de escritura, lo que lo vaciará.
            FileOutputStream fileOutputStream = openFileOutput("txtagregar.txt", MODE_PRIVATE);
            fileOutputStream.close();

            // Actualizar la vista para reflejar que los datos han sido eliminados.
            // txtAgregar.setText("");
            linearAgregar.removeAllViews();

            Toast.makeText(this, "Datos eliminados correctamente", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al eliminar los datos", Toast.LENGTH_SHORT).show();
        }
    }
}
