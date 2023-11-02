package com.example.aplicacionmantenimiento;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class historial_mantenimientos extends AppCompatActivity {
    private ListView listRecibir;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> mantenimientosKeys; // Para almacenar las claves únicas de los mantenimientos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_mantenimientos);

        listRecibir = findViewById(R.id.listrecibir);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        listRecibir.setAdapter(adapter);
        mantenimientosKeys = new ArrayList<>();

        // Inicializa la referencia a la base de datos Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mantenimientosRef = database.getReference("mantenimientos");

        // Escucha los cambios en la base de datos Firebase y actualiza el adaptador y las claves
        mantenimientosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();
                mantenimientosKeys.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String mantenimientoKey = ds.getKey(); // Obtén la clave única del mantenimiento
                    mantenimientosKeys.add(mantenimientoKey);
                    String titulo = ds.child("titulo").getValue(String.class);
                    String descripcion = ds.child("descripcion").getValue(String.class);

                    String mantenimientoData = "Título: " + titulo + "\nDescripción: " + descripcion;

                    // Agrega un botón de "Editar" y "Eliminar" junto a cada elemento de la lista
                    String elementoLista = mantenimientoData + " [Editar] [Eliminar]";
                    adapter.add(elementoLista);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Error al leer el valor
            }
        });

        // Maneja la selección de elementos en la lista
        listRecibir.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtén la clave del mantenimiento seleccionado
                final String mantenimientoKey = mantenimientosKeys.get(position);

                // Muestra un cuadro de diálogo de opciones (Editar o Eliminar)
                AlertDialog.Builder builder = new AlertDialog.Builder(historial_mantenimientos.this);
                builder.setTitle("Opciones")
                        .setItems(new CharSequence[]{"Editar", "Eliminar"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    // Editar el mantenimiento
                                    editarMantenimiento(mantenimientoKey);
                                } else if (which == 1) {
                                    // Eliminar el mantenimiento
                                    eliminarMantenimiento(mantenimientoKey);
                                }
                            }
                        });
                builder.create().show();
            }
        });
    }

    // Método para editar un mantenimiento
    private void editarMantenimiento(final String mantenimientoKey) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Editar Mantenimiento");

        // Agrega campos de edición para título y descripción
        final EditText tituloEditText = new EditText(this);
        tituloEditText.setHint("Título");
        final EditText descripcionEditText = new EditText(this);
        descripcionEditText.setHint("Descripción");

        builder.setView(tituloEditText);
        builder.setView(descripcionEditText);

        // Recupera los datos actuales del mantenimiento
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mantenimientosRef = database.getReference("mantenimientos").child(mantenimientoKey);
        mantenimientosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String tituloActual = dataSnapshot.child("titulo").getValue(String.class);
                String descripcionActual = dataSnapshot.child("descripcion").getValue(String.class);
                tituloEditText.setText(tituloActual);
                descripcionEditText.setText(descripcionActual);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar errores
            }
        });

        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nuevoTitulo = tituloEditText.getText().toString();
                String nuevaDescripcion = descripcionEditText.getText().toString();

                // Actualiza los datos en Firebase
                DatabaseReference mantenimientosRef = FirebaseDatabase.getInstance().getReference("mantenimientos").child(mantenimientoKey);
                mantenimientosRef.child("titulo").setValue(nuevoTitulo);
                mantenimientosRef.child("descripcion").setValue(nuevaDescripcion);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // No hacer nada si se selecciona "Cancelar"
            }
        });

        builder.create().show();
    }

    // Método para eliminar un mantenimiento
    private void eliminarMantenimiento(String mantenimientoKey) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mantenimientosRef = database.getReference("mantenimientos");

        // Elimina el mantenimiento de Firebase
        mantenimientosRef.child(mantenimientoKey).removeValue();
    }
}
