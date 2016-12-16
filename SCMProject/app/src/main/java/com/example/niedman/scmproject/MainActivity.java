package com.example.niedman.scmproject;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import static android.provider.AlarmClock.EXTRA_MESSAGE;
import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.niedman.scmproject.MESSAGE";
    TextView response;
    EditText editTextAddress, editTextPort;
    Button buttonConnect, buttonClear;
    Handler handler = new Handler();
    Intent intentaux ;

   // ArrayList <Intent> lista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("onCreat","------------onCreate");


        //editTextAddress = (EditText) findViewById(R.id.addressEditText);
        //editTextPort = (EditText) findViewById(R.id.portEditText);
        buttonConnect = (Button) findViewById(R.id.connectButton);
        buttonClear = (Button) findViewById(R.id.clearButton);
        response = (TextView) findViewById(R.id.responseTextView);

        buttonConnect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Client myClient = new Client("194.210.174.59", 12345, response);
                myClient.execute();
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                changeView(response);


            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                response.setText("-1");
            }
        });
    }


    private Runnable updateView = new Runnable(){

        @Override
        public void run() {
            Log.d("entrei","run");
            String message = response.getText().toString();
            Log.d("entrei",message);
            intentaux.putExtra(EXTRA_MESSAGE, message);
            //response.setText(message);

            handler.postDelayed(this, 10000);


                startActivity(intentaux);

        }

    };


    public void changeView(View view){

        Intent intent = new Intent(this,DisplayMessageActivity.class);
        intentaux=intent;
        handler.removeCallbacks(updateView);
        handler.post(updateView);
        String message = response.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);

        startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        //finish();
    }

}

