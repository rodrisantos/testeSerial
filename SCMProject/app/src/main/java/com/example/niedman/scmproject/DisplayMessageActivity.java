package com.example.niedman.scmproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicInteger;

import static android.graphics.Color.*;
import static java.lang.Thread.sleep;

public class DisplayMessageActivity extends AppCompatActivity {
    TextView response;
    Handler handler = new Handler();
    TextView textView;
    ViewGroup layout;
    AtomicInteger uvVal=new AtomicInteger(0);
    AtomicInteger tempVal=new AtomicInteger(0);
    String messageLow = "If you burn easily, cover up and use broad spectrum SPF 30+ sunscreen.";
    String messageModerate = "Generously apply broad spectrum SPF 30+ sunscreen every 2 hours, even on cloudy days, and after swimming or sweating. ";
    String messageHigh = "Reduce time in the sun between 10 a.m. and 4 p.m.\n" +
            "Generously apply broad spectrum SPF 30+ sunscreen every 2 hours, even on cloudy days, and after swimming or sweating. ";
    String messageVeryHigh="Reduce time in the sun between 10 a.m. and 4 p.m.\nGenerously apply broad spectrum SPF 30+ sunscreen every 2 hours, even on cloudy days, and after swimming or sweating.";
    String messageExtreme = "Reduce time in the sun between 10 a.m. and 4 p.m.\nGenerously apply broad spectrum SPF 30+ sunscreen every 2 hours, even on cloudy days, and after swimming or sweating.Watch out for bright surfaces, like sand, water and snow, which reflect UV and increase exposure.";
    TextView advices;


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

        response = (TextView) findViewById(R.id.responseTextView);



        Client myClient = new Client("194.210.172.69", 12345, uvVal, tempVal);
        myClient.execute();
        try {
            sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }





        Intent intent = getIntent();
        //message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        //message=Integer.toString(valor);
        textView = new TextView(this);


        TextView titulo = (TextView) findViewById(R.id.titulo);
        titulo.setTextSize(50);
        titulo.setText("Smart UV");
        
        titulo.setTextColor(BLUE);
        titulo.setX(150);
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

        advices = (TextView) findViewById(R.id.advice);
        advices.setTextSize(18);

        advices.setX(0);
        advices.setY(790);

        layout = (ViewGroup) findViewById(R.id.activity_display_message);
        layout.addView(textView);

        layout.setBackgroundColor(color);
        handler.post(updateView);
        //finish();

    }


    private Runnable updateView = new Runnable(){

        @Override
        public void run() {
            int valor = 800;
            int color = 0;
            String aux[];
            Log.d("entrei", "run");
            String message = uvVal.toString() + "\n" + tempVal.toString();
            if (message.equals(""))
                valor = -1;
            else {
                aux = message.split("\n");
                valor = Integer.parseInt(aux[0]);
            }

            Log.d("valor=", Integer.toString(valor));
            textView.setText(message);
            textView.setTextSize(30);
            textView.setText(message);
            textView.setX(500);
            textView.setY(600);
//            layout.addView(textView);
            if (valor > 0 && valor < 341){
                advices.setText(messageLow);
                color = GREEN;
            }
            if (valor>=341&&valor<682) {
                advices.setText(messageModerate);
                color = YELLOW;
            }
            if (valor>=682) {
                advices.setText(messageExtreme);
                color = RED;
            }
            layout.setBackgroundColor(color);




            handler.postDelayed(this, 10000);


        }

    };


    public void onBackPressed (){
        //Intent intent = new Intent(this,MainActivity.class);
        handler.removeCallbacksAndMessages(updateView);

        startActivity(new Intent(getApplicationContext(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }
}
