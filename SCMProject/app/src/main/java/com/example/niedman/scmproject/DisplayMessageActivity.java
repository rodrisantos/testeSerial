package com.example.niedman.scmproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.atomic.AtomicInteger;

import data.Channel;
import data.Item;
import services.WeatherServiceCallback;
import services.YahooWeatherService;

import static android.R.color.holo_orange_light;
import static android.graphics.Color.*;
import static java.lang.Thread.sleep;

public class DisplayMessageActivity extends AppCompatActivity implements WeatherServiceCallback {
    TextView response;
    Handler handler = new Handler();
    TextView textView;
    ImageView weatherIconImageView;
    TextView tempAPI;
    TextView location;
    ViewGroup layout;
    TextView condition;
    private YahooWeatherService service;
    private ProgressDialog dialog;


    AtomicInteger uvVal=new AtomicInteger(0);
    AtomicInteger tempVal=new AtomicInteger(0);
    String messageLow = "-Wear sunglasses on bright days.\n" +
            "-If you burn easily, cover up and use broad spectrum SPF 30+ sunscreen.\n" +
            "-Watch out for bright surfaces, like sand, water and snow, which reflect UV and increase exposure.";
    String messageModerate = "-Stay in shade near midday when the sun is strongest.\n" +
            "-If outdoors, wear protective clothing, a wide-brimmed hat, and UV-blocking sunglasses.\n" +
            "-Generously apply broad spectrum SPF 30+ sunscreen every 2 hours, even on cloudy days, and after swimming or sweating. \n" +
            "-Watch out for bright surfaces, like sand, water and snow, which reflect UV and increase exposure.";
    String messageHigh = "-Reduce time in the sun between 10 a.m. and 4 p.m.\n" +
            "-If outdoors, seek shade and wear protective clothing, a wide-brimmed hat, and UV-blocking sunglasses.\n" +
            "-Generously apply broad spectrum SPF 30+ sunscreen every 2 hours, even on cloudy days, and after swimming or sweating. \n" +
            "-Watch out for bright surfaces, like sand, water and snow, which reflect UV and increase exposure.";
    String messageVeryHigh="-Minimize sun exposure between 10 a.m. and 4 p.m.\n" +
            "-If outdoors, seek shade and wear protective clothing, a wide-brimmed hat, and UV-blocking sunglasses.\n" +
            "-Generously apply broad spectrum SPF 30+ sunscreen every 2 hours, even on cloudy days, and after swimming or sweating. \n" +
            "-Watch out for bright surfaces, like sand, water and snow, which reflect UV and increase exposure.";
    String messageExtreme = "-Try to avoid sun exposure between 10 a.m. and 4 p.m.\n" +
            "-If outdoors, seek shade and wear protective clothing, a wide-brimmed hat, and UV-blocking sunglasses.\n" +
            "-Generously apply broad spectrum SPF 30+ sunscreen every 2 hours, even on cloudy days, and after swimming or sweating.\n" +
            "-Watch out for bright surfaces, like sand, water and snow, which reflect UV and increase exposure.";
    TextView advices;
    int valor = 800;

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
        weatherIconImageView = (ImageView) findViewById(R.id.weatherIconImageView);
        tempAPI = (TextView) findViewById(R.id.tempAPI);
        location = (TextView) findViewById(R.id.location);
        condition = (TextView) findViewById(R.id.condition);
        service= new YahooWeatherService(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();
        service.refreshWeather("Lisbon, Portugal");

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
        titulo.setTextSize(40);
        titulo.setText("Smart UV");
        
        titulo.setTextColor(BLUE);
        titulo.setX(0);
        titulo.setY(0);

        TextView uv = (TextView) findViewById(R.id.uv);
        uv.setTextSize(24);
        uv.setText("Uv:");
        uv.setX(200);
        uv.setY(250);

        TextView temp = (TextView) findViewById(R.id.Temp);
        temp.setTextSize(24);
        temp.setText("Temp:");
        temp.setX(0);
        temp.setY(300);

        TextView tituloAdvice = (TextView) findViewById(R.id.tituloAdvice);
        tituloAdvice.setTextSize(24);
        tituloAdvice.setTextColor(BLUE);
        tituloAdvice.setText("Advices");
        tituloAdvice.setX(350);
        tituloAdvice.setY(600);

        advices = (TextView) findViewById(R.id.advice);
        advices.setTextSize(18);

        advices.setX(0);
        advices.setY(720);

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
            int nivel=0;
            String aux[] = new String[2];
            String mensagemAux="";
            Log.d("entrei", "run");
            String message = uvVal.toString() + "\n" + tempVal.toString();
            if (message.equals(""))
                valor = -1;
            else {
                aux = message.split("\n");
                valor = Integer.parseInt(aux[0]);
            }
            nivel=verificaNivel(valor);
            mensagemAux=(Integer.toString(nivel)+"\n"+aux[1]);
            Log.d("valor=", Integer.toString(nivel));

            textView.setText(mensagemAux);
            textView.setTextSize(30);
            textView.setTextSize(30);
            //textView.setText(message);
            textView.setX(500);
            textView.setY(320);
//            layout.addView(textView);
            if (valor > 0 && valor < 186){
                advices.setTextSize(18);
                advices.setText(messageLow);
                color = GREEN;
            }
            if (valor>=186&&valor<465) {
                advices.setTextSize(16);
                advices.setText(messageModerate);
                color = YELLOW;
            }
            if (valor>=465&&valor<651) {
                advices.setTextSize(16);
                advices.setText(messageHigh);

                color = Color.parseColor("#FFA500");
            }
            if (valor >=651&&valor<930){
                advices.setTextSize(16);
                advices.setText(messageVeryHigh);
                color = RED;
            }
            if (valor >=930){
                advices.setTextSize(16);

                advices.setText(messageExtreme);
                color = Color.parseColor("#800080");
            }
            layout.setBackgroundColor(color);




            handler.postDelayed(this, 10000);





        }

        private int verificaNivel(int valor) {
                int i=0;
                while (valor>0) {
                    valor -= 93;
                    i++;
                }
                return i;

        }

    };


    public void onBackPressed (){
        //Intent intent = new Intent(this,MainActivity.class);
        handler.removeCallbacksAndMessages(updateView);

        startActivity(new Intent(getApplicationContext(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }

    @Override
    public void serviceSuccess(Channel channel) {
        dialog.hide();
        Item item = channel.getItem();
        int resourceId = getResources().getIdentifier("mipmap/icon_"+channel.getItem().getCondition().getCode(),null,getPackageName());

        @SuppressWarnings("deprecation")
        Drawable weatherIconDrawable = getResources().getDrawable(resourceId);

        weatherIconImageView.setImageDrawable(weatherIconDrawable);
        location.setText(service.getLocation());
        tempAPI.setText(item.getCondition().getTemperature()+ "\u00B0" + channel.getUnits().getTemperature());
        condition.setText(item.getCondition().getDescription());
    }

    @Override
    public void serviceFailed(Exception exception) {
        dialog.hide();
        Toast.makeText(this,exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
