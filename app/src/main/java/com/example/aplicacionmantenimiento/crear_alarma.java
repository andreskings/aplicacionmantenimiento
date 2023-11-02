package com.example.aplicacionmantenimiento;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class crear_alarma extends AppCompatActivity {
    private TimePicker timePicker;
    private Button btnProgramarAlarma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_alarma);

        timePicker = findViewById(R.id.timePicker);
        btnProgramarAlarma = findViewById(R.id.btnGuardarAlarma);

        btnProgramarAlarma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                programarAlarma();
            }
        });
    }

    private void programarAlarma() {
        // Obtener la hora y minutos seleccionados en el TimePicker
        int hora = timePicker.getHour();
        int minutos = timePicker.getMinute();

        // Configurar el AlarmManager
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        // Configurar la hora de la alarma
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hora);
        calendar.set(Calendar.MINUTE, minutos);

        // Programar la alarma
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}
