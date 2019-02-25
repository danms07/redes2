import java.net.*;
import java.io.*;

public class SEcoUDPB{
  public static void main(String []args){
    try{
      DatagramSocket s=new DatagramSocket(2000);
      System.out.println("Servidor iniciado");
      StringBuilder sb=new StringBuilder();
      for(;;){
        DatagramPacket p=new DatagramPacket(new byte[2000],2000);
        do{
          s.receive(p);//Bloqueo
          System.out.println("Datagrama recibido desde: "+p.getAddress()+":"+p.getPort());
          sb.append(new String(p.getData(),0,p.getLength()));
        }while(p.getLength()!=0);

        String msg =sb.toString();
        System.out.println("Con el mensaje: "+msg);
        System.out.println("Regresando mensaje");
        //DatagramPacket pp=new DatagramPacket();
        byte []b=msg.getBytes();
        if(b.length>20){
          DataInputStream dis=new DataInputStream(new ByteArrayInputStream(b));
          byte []bb=new byte[20];
          int n=0;
          while((n=dis.read(bb,0,20))!=0){
              DatagramPacket dp=new DatagramPacket(bb,bb.length);
              s.send(dp);
          }
              
          
          
        }
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}
