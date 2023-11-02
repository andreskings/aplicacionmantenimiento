package com.example.aplicacionamantenimiento;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.aplicacionmantenimiento.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class historial_mantenimientos extends AppCompatActivity {
    private ListView listRecibir;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.aplicacionmantenimiento.R.layout.activity_historial_mantenimientos);

        listRecibir = findViewById(R.id.listrecibir);

        // Inicializa el adaptador
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
        listRecibir.setAdapter(adapter);

        // Inicializa la referencia a la base de datos Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mantenimientosRef = database.getReference("mantenimientos");

        // Escucha los cambios en la base de datos Firebase y actualiza el adaptador
        mantenimientosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String titulo = ds.child("titulo").getValue(String.class);
                    String descripcion = ds.child("descripcion").getValue(String.class);

                    adapter.add("Título: " + titulo + "\nDescripción: " + descripcion);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Error al leer el valor
            }
        });
    }
}
