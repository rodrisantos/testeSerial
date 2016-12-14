import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by rsantos on 12/14/16.
 */
public class TCPCliente extends Thread implements Runnable {
    AtomicInteger valor;
    Socket socket;

    public TCPCliente(AtomicInteger valor,Socket socket)  {
        this.valor=valor;
        this.socket=socket;
    }
    public void run(){
        String dados="";
        while(true) {
            try {
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                dados=Integer.toString(valor.get());
                output.writeBytes(dados+"\n");
                output.flush();
                System.out.println("valor----------->"+valor.get());
                try {
                    sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                System.out.println("server closed");
                return;
                //e.printStackTrace();
            }
        }
    }
}
