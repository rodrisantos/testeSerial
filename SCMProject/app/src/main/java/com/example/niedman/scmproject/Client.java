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
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

public class Client extends AsyncTask<Void, Integer, Void> {

    String dstAddress;
    int dstPort;
    String response = "";
    TextView textResponse;
    int count=0;

    Client(String addr, int port, TextView textResponse) {
        dstAddress = addr;
        dstPort = port;
        this.textResponse = textResponse;
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        Recebe.doStuff(this,dstAddress,dstPort);

        return null;
    }

    public void doProgress(int value){
        publishProgress(value);
    }


    protected void onProgressUpdate(Integer... value){
        Log.e("cheguei aqui","");
        textResponse.clearComposingText();
        textResponse.setText(value[0].toString());
        //super.onProgressUpdate();
    }

    protected void onPostExecute(Void result) {
        //textResponse.setText(response);
        //super.onPostExecute(result);
    }


}


class Recebe{

    public static void doStuff(Client task, String dstAddress, int dstPort){

        Socket socket = null;
        String response = "";

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

                task.doProgress(value);
                response="";

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