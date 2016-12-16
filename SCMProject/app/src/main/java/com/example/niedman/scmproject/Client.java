package com.example.niedman.scmproject;

/**
 * Created by niedman on 12/8/16.
 */
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class Client extends AsyncTask<Void, Integer, Void> {

    String dstAddress;
    int dstPort;
    String response = "-1";
    TextView textResponse;
    AtomicInteger uv;
    AtomicInteger temp;
    int count=0;

    Client(String addr, int port, TextView textResponse) {
        dstAddress = addr;
        dstPort = port;
        this.textResponse = textResponse;
    }

    public Client(String addr, int port, AtomicInteger uv, AtomicInteger temp) {
        dstAddress = addr;
        dstPort = port;
        this.uv=uv;
        this.temp=temp;

    }

    @Override
    protected Void doInBackground(Void... arg0) {
        Recebe.doStuff(this,dstAddress,dstPort,uv,temp);

        return null;
    }

    public void doProgress(int value, int value2){
        publishProgress(value,value2);
    }


    protected void onProgressUpdate(Integer... value){
        Log.e("cheguei aqui","");
        String texto="";
        MainActivity main=new MainActivity();
        textResponse.clearComposingText();
        texto+=value[0].toString()+"\n"+value[1].toString();
        textResponse.setText(texto);
        //super.onProgressUpdate();
    }

    protected void onPostExecute(Void result) {
        //textResponse.setText(response);
        //super.onPostExecute(result);
    }


}


class Recebe{


    public static void doStuff(Client task, String dstAddress, int dstPort, AtomicInteger uv, AtomicInteger temp){


        Socket socket = null;
        String response = "-1";

        try {
            socket = new Socket(dstAddress, dstPort);

            //ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
            //byte[] buffer = new byte[1024];

            //int bytesRead;
            InputStream inputStream = socket.getInputStream();
            BufferedReader bread = new BufferedReader(new InputStreamReader(inputStream));
         /*
          * notice: inputStream.read() will block if no data return
          */
            Void result=null;
            while (true) {

                //byteArrayOutputStream.flush();

                //bytesRead = bread.readLine();
                response=bread.readLine();

                //response = byteArrayOutputStream.toString("UTF-8");
                int value=Integer.parseInt(response);

                int value2=-1;
                uv.set(value);
                temp.set(value2);
                //task.doProgress(value,value2);
                response="-1";

                //byteArrayOutputStream.flush();
                //response = bytesRead;
                //onProgressUpdate(result);
            }

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "IOException: " + e.toString();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
}