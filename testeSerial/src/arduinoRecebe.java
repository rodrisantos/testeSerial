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
    AtomicInteger valor;
    int aux=0;
    String val="";
    arduinoRecebe(AtomicInteger valor){
        this.valor=valor;
    }

    public void run(){
         /*SerialPort serialPort = new SerialPort("/dev/ttyACM0");
        try {
            System.out.println("Port opened: " + serialPort.openPort());

           try {
                sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(testeSerial.class.getName()).log(Level.SEVERE, null, ex);
            }*/

            //serialPort.setParams(9600, 8, 1, 0);

            while(true){
                Random rand = new Random();
                valor.set(rand.nextInt(1023));
                //System.out.println(valor.get());
                try {
                    sleep(10000);
                } catch (InterruptedException e) {
                    System.err.println("erro no sleep");
                }

            }
                /*int guarda=0;
                int i=0;
                val=serialPort.readString();
                if(val!=null) {
                    for (i = 0; i < val.length(); i++) {
                        if (Character.getNumericValue(val.charAt(i)) >= 0 && Character.getNumericValue(val.charAt(i)) < 10) {
                            //System.out.println(val.charAt(i));
                            guarda *= 10;
                            guarda += Character.getNumericValue(val.charAt(i));
                        }
                    }
                }
                //aux=Integer.parseInt(val);
                //System.out.println("thread "+guarda);
                valor.set(guarda);


            }


            //System.out.println("Port closed: " + serialPort.closePort());*/

        //}
        /*catch (SerialPortException ex){
            System.out.println(ex);
        }*/
    }

    private int testa(char c) {
        if((int)c>=0&&(int)c<10)
            return (int) c;

        return -1;
    }


}
