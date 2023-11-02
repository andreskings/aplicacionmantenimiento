package com.example.aplicacionmantenimiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class agregar_mantenimiento extends AppCompatActivity {
    private EditText editTextTitulo;
    private EditText editTextDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_mantenimiento);

        editTextTitulo = findViewById(R.id.titulomantenimiento);
        editTextDescripcion = findViewById(R.id.descripcion);

        Button btnGuardar = findViewById(R.id.btnguardarman);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarInformacionEnFirebase();
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
                // Aquí deberás agregar código para configurar una alarma
                // Por ejemplo, puedes abrir una nueva actividad para configurar la alarma o mostrar un diálogo de configuración de alarma.
                // Asegúrate de implementar la lógica de configuración de la alarma según tus necesidades.
                // Ejemplo de cómo abrir una nueva actividad:
                Intent intent = new Intent(agregar_mantenimiento.this, crear_alarma.class);
                startActivity(intent);
            }
        });

    }

    private void guardarInformacionEnFirebase() {
        String titulo = editTextTitulo.getText().toString();
        String descripcion = editTextDescripcion.getText().toString();

        // Inicializa la referencia a la base de datos Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("mantenimientos");

        // Crea un nuevo nodo con una clave única y establece los datos
        DatabaseReference nuevoMantenimientoRef = ref.push();
        nuevoMantenimientoRef.child("titulo").setValue(titulo, (databaseError, databaseReference) -> {
            if (databaseError != null) {
                // Handle the error here, e.g., Log.e("FirebaseError", databaseError.getMessage());
                Toast.makeText(agregar_mantenimiento.this, "Error al guardar los datos en Firebase", Toast.LENGTH_SHORT).show();
            } else {
                // Datos guardados con éxito
                Toast.makeText(agregar_mantenimiento.this, "Datos guardados correctamente en Firebase", Toast.LENGTH_SHORT).show();
            }
        });
        nuevoMantenimientoRef.child("descripcion").setValue(descripcion);
    }

    private void limpiarDatos() {
        // Puedes implementar esto si deseas limpiar los campos de texto
        editTextTitulo.setText("");
        editTextDescripcion.setText("");
    }
}
