import java.io.*;
import java.net.*;

public class CEcoUDPB{
  public static void main(String []args){
    try{
      DatagramSocket cl=new DatagramSocket(3000);
      System.out.println("Cliente iniciado, escriba un mensaje");
      BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
      String mensaje=br.readLine();
      byte []b=mensaje.getBytes();
      String dst="127.0.0.1";
      int pto=2000;
      DatagramPacket p=new DatagramPacket(b,b.length,InetAddress.getByName(dst),pto);
      cl.send(p);
      cl.close();
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}
