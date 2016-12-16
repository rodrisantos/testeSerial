package com.example.niedman.scmproject;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;
import java.util.StringTokenizer;

import static android.graphics.Color.*;
import static java.lang.Thread.sleep;

public class DisplayMessageActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int color=WHITE;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        int valor=-1;
        String message="";
        String aux[];
        //int color=0;
        Random rand= new Random();
        Intent intent = getIntent();
        message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        if(message.equals(""))
            valor=-1;
        else {
            aux=message.split("\n");
            valor = Integer.parseInt(aux[0]);
        }
        //message=Integer.toString(valor);
        TextView textView = new TextView(this);
        textView.setTextSize(30);
        textView.setText(message);
        textView.setX(400);
        textView.setY(550);

        TextView titulo = (TextView) findViewById(R.id.titulo);
        titulo.setTextSize(50);
        titulo.setText("Info Weather");
        
        titulo.setTextColor(BLUE);
        titulo.setX(50);
        titulo.setY(0);

        TextView uv = (TextView) findViewById(R.id.uv);
        uv.setTextSize(38);
        uv.setText("Uv:");
        uv.setX(200);
        uv.setY(520);

        TextView temp = (TextView) findViewById(R.id.Temp);
        temp.setTextSize(38);
        temp.setText("Temp:");
        temp.setX(0);
        temp.setY(590);

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_display_message);
        layout.addView(textView);
        //layout.addView(uv);
        if (valor>0&&valor<341)
            color=GREEN;
        if (valor>=341&&valor<682)
            color=YELLOW;
        if (valor>=682)
            color=RED;
        layout.setBackgroundColor(color);

        //finish();

    }


    public void onBackPressed (){
        //Intent intent = new Intent(this,MainActivity.class);
        startActivity(new Intent(getApplicationContext(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }
}
