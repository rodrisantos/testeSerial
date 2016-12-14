package com.example.niedman.scmproject;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.niedman.scmproject.MESSAGE";
    TextView response;
    EditText editTextAddress, editTextPort;
    Button buttonConnect, buttonClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //editTextAddress = (EditText) findViewById(R.id.addressEditText);
        //editTextPort = (EditText) findViewById(R.id.portEditText);
        buttonConnect = (Button) findViewById(R.id.connectButton);
        buttonClear = (Button) findViewById(R.id.clearButton);
        response = (TextView) findViewById(R.id.responseTextView);

        buttonConnect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Client myClient = new Client("194.210.174.222", 12345, response);
                myClient.execute();
                //changeView();
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                response.setText("");
            }
        });
    }

    public void changeView(View view){

        Intent intent = new Intent(this,DisplayMessageActivity.class);
        String message = response.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}

