package com.example.aplicacionmantenimiento;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class crear_alarma extends AppCompatActivity {
    private Button[] btnSeleccionarFechaHora;
    private Button btnGuardarAlarma;
    private TextView[] textViewFechaSeleccionada, textViewHoraSeleccionada;
    private Calendar[] selectedDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_alarma);

        // Inicializar botones y textViews originales
        btnSeleccionarFechaHora = new Button[4];
        btnSeleccionarFechaHora[0] = findViewById(R.id.btnSeleccionarFechaHora1);
        btnSeleccionarFechaHora[1] = findViewById(R.id.btnSeleccionarFechaHora2);
        btnSeleccionarFechaHora[2] = findViewById(R.id.btnSeleccionarFechaHora3);
        btnSeleccionarFechaHora[3] = findViewById(R.id.btnSeleccionarFechaHora4);


        btnGuardarAlarma = findViewById(R.id.btnGuardarAlarma);

        textViewFechaSeleccionada = new TextView[4];
        textViewFechaSeleccionada[0] = findViewById(R.id.textViewFechaSeleccionada1);
        textViewFechaSeleccionada[1] = findViewById(R.id.textViewFechaSeleccionada2);
        textViewFechaSeleccionada[2] = findViewById(R.id.textViewFechaSeleccionada3);
        textViewFechaSeleccionada[3] = findViewById(R.id.textViewFechaSeleccionada4);

        textViewHoraSeleccionada = new TextView[4];
        textViewHoraSeleccionada[0] = findViewById(R.id.textViewHoraSeleccionada1);
        textViewHoraSeleccionada[1] = findViewById(R.id.textViewHoraSeleccionada2);
        textViewHoraSeleccionada[2] = findViewById(R.id.textViewHoraSeleccionada3);
        textViewHoraSeleccionada[3] = findViewById(R.id.textViewHoraSeleccionada4);


        // Inicializar el array de instancias de Calendar
        selectedDateTime = new Calendar[4];
        for (int i = 0; i < 4; i++) {
            selectedDateTime[i] = Calendar.getInstance();
        }

        // Asignar listeners a los botones
        for (int i = 0; i < 4; i++) {
            final int index = i;
            btnSeleccionarFechaHora[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mostrarSelectorFechaHora(index);
                }
            });
        }

        btnGuardarAlarma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                programarAlarma();
            }
        });
    }

    private void mostrarSelectorFechaHora(final int index) {
        // Mostrar un DatePickerDialog para seleccionar la fecha
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                selectedDateTime[index].set(Calendar.YEAR, year);
                selectedDateTime[index].set(Calendar.MONTH, month);
                selectedDateTime[index].set(Calendar.DAY_OF_MONTH, dayOfMonth);

                // Mostrar un TimePickerDialog para seleccionar la hora
                TimePickerDialog timePickerDialog = new TimePickerDialog(crear_alarma.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedDateTime[index].set(Calendar.HOUR_OF_DAY, hourOfDay);
                        selectedDateTime[index].set(Calendar.MINUTE, minute);

                        // Actualizar los TextView con la fecha y hora seleccionadas
                        actualizarTextViewsFechaHora(index);
                    }
                }, selectedDateTime[index].get(Calendar.HOUR_OF_DAY), selectedDateTime[index].get(Calendar.MINUTE), true);

                timePickerDialog.show();
            }
        }, selectedDateTime[index].get(Calendar.YEAR), selectedDateTime[index].get(Calendar.MONTH), selectedDateTime[index].get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void actualizarTextViewsFechaHora(int index) {
        String fechaSeleccionada = android.text.format.DateFormat.format("dd-MM-yyyy", selectedDateTime[index]).toString();
        String horaSeleccionada = android.text.format.DateFormat.format("HH:mm", selectedDateTime[index]).toString();

        textViewFechaSeleccionada[index].setText("Fecha seleccionada: " + fechaSeleccionada);
        textViewHoraSeleccionada[index].setText("Hora seleccionada: " + horaSeleccionada);
    }

    private void programarAlarma() {
        // Obtener una instancia de AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Recorrer la lista de botones y programar una alarma para cada uno
        for (int i = 0; i < 4; i++) {
            Calendar alarmTime = selectedDateTime[i];

            // Crear un Intent que se enviará cuando suene la alarma
            Intent intent = new Intent(this, AlarmReceiver.class);
            int alarmId = i; // Utilizar el índice como identificador único
            intent.putExtra("ALARM_ID", alarmId);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, alarmId, intent, PendingIntent.FLAG_IMMUTABLE);

            // Configurar el calendario con la hora y minuto seleccionados
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, alarmTime.get(Calendar.YEAR));
            calendar.set(Calendar.MONTH, alarmTime.get(Calendar.MONTH));
            calendar.set(Calendar.DAY_OF_MONTH, alarmTime.get(Calendar.DAY_OF_MONTH));
            calendar.set(Calendar.HOUR_OF_DAY, alarmTime.get(Calendar.HOUR_OF_DAY));
            calendar.set(Calendar.MINUTE, alarmTime.get(Calendar.MINUTE));
            calendar.set(Calendar.SECOND, 0);

            // Programar la alarma
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            // Mostrar un mensaje con Toast
            Toast.makeText(this, "Alarma programada para: " + android.text.format.DateFormat.format("dd-MM-yyyy HH:mm", calendar), Toast.LENGTH_SHORT).show();

            // Agregar logs de depuración
            Log.d("Alarma", "Alarma programada para: " + calendar.getTime().toString());
        }
    }
}
