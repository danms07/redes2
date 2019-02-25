import java.net.*;
import java.io.*;

public class SEcoTCPB{
  public static void main(String []args){
    try{
      ServerSocket s=new ServerSocket(1234);
      System.out.println("Esperando cliente");
      for(;;){
        Socket cl=s.accept();
        System.out.println("Conexión establecida desde "+cl.getInetAddress()+":"+cl.getPort());
        String mensaje="Hola mundo";
        PrintWriter pw=new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
        pw.println(mensaje);
        pw.flush();
        String respuesta=new BufferedReader(new InputStreamReader(cl.getInputStream())).readLine();
        System.out.println("Mensaje del cliente: "+respuesta);
        pw.close();
        cl.close();
      }
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }
}
