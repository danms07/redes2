import java.net.*;
import java.io.*;

public class CecoTCPB{
  public static void main(String []args){
    try{
      BufferedReader R1=new BufferedReader(new InputStreamReader(System.in));
      System.out.println("Escribe la dirección del servidor ");
      String host=R1.readLine();
      System.out.println("Escriba el puerto ");
      int pto=Integer.parseInt(R1.readLine());
      Socket cl=new Socket(host,pto);
      BufferedReader br2=new BufferedReader(new InputStreamReader(cl.getInputStream()));
      String mensaje=br2.readLine();
      System.out.println("Recibimos un mensaje desde el servidor");
      System.out.println("Mensaje: "+mensaje);
      String respuesta="Hola servidor";
      PrintWriter pw=new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
      pw.println(respuesta);
      pw.flush();
      R1.close();
      br2.close();
      cl.close();
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }
}
