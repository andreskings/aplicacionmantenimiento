package com.example.aplicacionmantenimiento;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;
import com.example.aplicacionmantenimiento.MainActivity;
import com.example.aplicacionmantenimiento.R;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 5000; // Tiempo de espera en milisegundos (5 segundos)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Obtener la referencia de la ImageView
        ImageView imageViewSplash = findViewById(R.id.imageView3);

        // Cargar el GIF con Glide
        Glide.with(this)
                .asGif()
                .load(R.drawable.gif_camion)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)) // Desactivar la caché para asegurar la animación
                .into(imageViewSplash);

        // Crear una animación de rotación
        imageViewSplash.post(new Runnable() {
            @Override
            public void run() {
                GifDrawable gifDrawable = (GifDrawable) imageViewSplash.getDrawable();
                if (gifDrawable != null) {
                    gifDrawable.setLoopCount(GifDrawable.LOOP_FOREVER); // Establecer bucle infinito
                    gifDrawable.start(); // Iniciar la animación
                }
            }
        });

        // Utilizar un Handler para esperar durante el tiempo definido y luego iniciar la siguiente actividad
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Crear un Intent para iniciar la siguiente actividad
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);

                // Asegurarse de que la actividad actual se cierre después de iniciar la siguiente actividad
                finish();
            }
        }, SPLASH_DELAY);
    }
}
