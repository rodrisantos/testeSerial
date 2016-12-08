import sun.rmi.runtime.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;
import static javax.print.attribute.standard.PrinterStateReason.STOPPING;

public class testeSerial {
    AtomicInteger valor=new AtomicInteger(0);
    public static void main(String[] args) throws InterruptedException {
        testeSerial main= new testeSerial();
        main.inicia();
        main.iniciaTCP();
    }

    private void iniciaTCP() {
        System.out.println("server");
        try {
            Boolean end = false;
            ServerSocket ss = new ServerSocket(12345);
            while(!end){
                //Server is waiting for client here, if needed
                Socket s = ss.accept();
                System.out.println("accept");
                //BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                PrintWriter output = new PrintWriter(s.getOutputStream(),true); //Autoflush
                //String st = input.readLine();
                //Log.d("Tcp Example", "From client: "+st);
                output.println(valor.get());
                s.close();
                //if ( STOPPING conditions){ end = true; }
            }
            ss.close();


        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    private void inicia() throws InterruptedException {
        Thread ardReceve=new arduinoRecebe(valor);

        ardReceve.start();





    }

}