/**
 * Created by rsantos on 12/8/16.
 */


import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortException;
import java.*;

import static java.lang.Thread.sleep;


public class arduinoRecebe extends Thread implements Runnable {
    AtomicInteger valorUv;
    AtomicInteger valorTemp;
    int aux=0;
    String val="";
    SerialPort serialPort;
    arduinoRecebe(AtomicInteger valor ,AtomicInteger valor2){
        this.valorUv=valor;
        this.valorTemp= valor2;
        serialPort = new SerialPort("/dev/ttyACM0");
    }

    public void run(){

        String valores[]=new String[2];
        valores[0]="";
        valores[1]="";

        try {
            System.out.println("Port opened: " + serialPort.openPort());

            try {
                sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(testeSerial.class.getName()).log(Level.SEVERE, null, ex);
            }

            serialPort.setParams(9600, 8, 1, 0);

            while(true){
                int guarda=0;
                int guarda2=0;
                int i=0;

                val=serialPort.readString();
                System.out.println(val);

                if(val!=null) {
                    valores=val.split("\n");
                    System.out.println(valores.length);
                    for (i = 0; i < valores[0].length(); i++) {
                        if (Character.getNumericValue(valores[0].charAt(i)) >= 0 && Character.getNumericValue(valores[0].charAt(i)) < 10) {
                            //System.out.println(val.charAt(i));
                            guarda*= 10;
                            guarda+= Character.getNumericValue(valores[0].charAt(i));
                        }
                    }
                    for (i = 0; i < valores[1].length(); i++) {
                        if (Character.getNumericValue(valores[1].charAt(i)) >= 0 && Character.getNumericValue(valores[1].charAt(i)) < 10) {
                            //System.out.println(val.charAt(i));
                            guarda2*= 10;
                            guarda2+= Character.getNumericValue(valores[1].charAt(i));
                        }
                    }
                }
                valorUv.set(guarda);
                valorTemp.set(guarda2);
                guarda=0;
                try {
                    sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //aux=Integer.parseInt(val);


                //System.out.println("thread "+valorUv.toString()+" "+valorTemp.toString());

            }
            //System.out.println("Port closed: " + serialPort.closePort());*/
        }
        catch (SerialPortException ex){
            System.out.println(ex);
        }
    }

    private int testa(char c) {
        if((int)c>=0&&(int)c<10)
            return (int) c;

        return -1;
    }


}
