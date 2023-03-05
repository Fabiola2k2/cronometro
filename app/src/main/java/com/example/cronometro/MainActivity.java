package com.example.cronometro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView tv_tiempo;
    private Button btn_inicar_parar, btn_reiniciar_vuelta;
    private ListView lv_laps;
    private int milisegundos = 0, segundos = 0, minutos = 0, ms;
    private int vuelta = 1;
    private boolean corriendo=false, start_stop=true;
    private ArrayList<String> lista_vueltas;
    private ArrayAdapter<String> aa_laps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_tiempo = findViewById(R.id.tv_tiempo);
        btn_inicar_parar = findViewById(R.id.btn_iniciar_parar);
        btn_reiniciar_vuelta = findViewById(R.id.btn_reiniciar_lap);
        lv_laps = findViewById(R.id.lv_laps);

        lista_vueltas = new ArrayList<>();
        aa_laps = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista_vueltas);

        lv_laps.setAdapter(aa_laps);

        iniciarTiempo();
        btn_reiniciar_vuelta.setEnabled(false);
        btn_inicar_parar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(start_stop){
                    corriendo = true;
                    btn_reiniciar_vuelta.setEnabled(true);
                    btn_inicar_parar.setText("Detener");
                    btn_reiniciar_vuelta.setText("Vuelta");
                    start_stop=false;
                }else{
                    corriendo = false;
                    btn_inicar_parar.setText("Iniciar");
                    btn_reiniciar_vuelta.setText("Reiniciar");
                    start_stop=true;
                }
            }
        });
        btn_reiniciar_vuelta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn_reiniciar_vuelta.getText().toString().equals("Reiniciar")){
                    btn_reiniciar_vuelta.setEnabled(false);
                    aa_laps.clear();
                    milisegundos=0;
                    segundos=0;
                    minutos=0;
                    vuelta = 1;
                }else{

                    String tiempo = String.format(Locale.getDefault(), "%02d:%02d:%02d",minutos,segundos,ms);
                    aa_laps.add(String.valueOf(vuelta)+"\t\t\t\t\t"+tiempo);
                    vuelta++;

                }
            }
        });


    }

    public void iniciarTiempo(){
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                ms = milisegundos%100;


                if(corriendo){
                    milisegundos++;
                    if (ms==0 && milisegundos!=1 ){
                        segundos++;
                    }
                    if(segundos==60){
                        minutos++;
                        segundos=0;
                    }
                }
                String formato = String.format(Locale.getDefault(), "%02d:%02d:%02d",minutos,segundos,ms);
                tv_tiempo.setText(formato);
                handler.postDelayed(this,0);
            }
        };
        handler.post(runnable);
    }
}