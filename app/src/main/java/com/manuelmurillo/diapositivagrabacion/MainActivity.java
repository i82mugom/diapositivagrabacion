package com.manuelmurillo.diapositivagrabacion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    String almacenamiento = null;

    MediaRecorder miaudio;

        Button botoniniciar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        botoniniciar = (Button)findViewById(R.id.buttoniniciar);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
        }


    }

    public void funciongrabacion(View view) {

        if( miaudio==null) {
            almacenamiento = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Grabacion.mp3";
            Log.i("miapp", "Ruta de almacenamiento: " + almacenamiento);
            miaudio = new MediaRecorder();
            miaudio.setAudioSource(MediaRecorder.AudioSource.MIC);
            miaudio.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            miaudio.setAudioEncoder(MediaRecorder.OutputFormat.AMR_WB);
            miaudio.setOutputFile(almacenamiento);
            Log.i("miapp", "Ruta de almacenamiento:a " + almacenamiento);
            try{
                miaudio.prepare();
                miaudio.start();
            }catch (IOException e){
                Toast.makeText(this, "Error en la preparación del audio", Toast.LENGTH_SHORT).show();

            }
            botoniniciar.setText("Grabando");

        }
        else {

            miaudio.stop();

            miaudio.release();
            miaudio=null;
            botoniniciar.setText("Iniciar a grabar");
        }

    }

    public void funcionreproduccion(View view) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try{
            mediaPlayer.setDataSource(almacenamiento);
            mediaPlayer.prepare();
        }catch (IOException e){
            Toast.makeText(this, "No es posible cargar el archivo", Toast.LENGTH_SHORT).show();
        }
        mediaPlayer.start();
        Toast.makeText(this, "Reproduciendo", Toast.LENGTH_SHORT).show();
    }
}
