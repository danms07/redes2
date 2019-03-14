import java.net.*;
import java.io.*;

public class MulticastServer2 extends Thread{
  public static final String MCAST_ADDR="230.0.0.1";
  public static final int MCAST_PORT=9013;
  public static final int DGRAM_BUFF_LEN=512;
  public void run(){
    String msg="Hola, soy servidor de Java";
    InetAddress group;
    try{
      group=InetAddress.getByname(MCAST_ADDR);
    }catch(UnknownHostException e){
      e.printStackTrace();
      System.exit(1);
    }
    for(;;){
      try{
        MulticastSocket socket=new MulticastSocket(MCAST_PORT);
        socket.joinGroup(group);
        DatagramPacket packet=new DatagramPacket(msg.getBytes(),msg.length(),group,MCAST_PORT);
        System.out.println("Enviando: "+msg+" Con un TTL="+socket.getTimeToLive());
        socket.send(packet);
        socket.close();
      }catch(IOException e){
        e.printStackTrace();
        System.exit(2);
      }
      try{
        Thread.sleep(5000);
      }catch(InterruptedException ie){

      }
    }//for
  }//run

  public static void main(String[] args) {
      MulticastServer2 ms2=new MulticastServer2();
      try{
        ms2.start();
      }catch(exception e){
        e.printStackTrace();
      }
  }
}
