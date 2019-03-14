import java.io.*;
import java.net.*;
import java.util.Vector;

public class MulticastClient2 extends Thread{
  public static final String MCAST_ADDR="230.0.0.1";
  public static final int MCAST_PORT=9013;
  public static final int DGRAM_BUFF_LEN=512;

  public void run(){
    InetAddress group=null;
    try{
      group=InetAddress.getByName(MCAST_ADDR);
    }catch(UnknownHostException e){
      e.printStackTrace();
      System.exit(1);
    }
    //Vector d=new Vector();
    boolean salta=true;
    try{
      MulticastSocket socket=new MulticastSocket(MCAST_PORT);
      socket.joinGroup(group);
      //int cd=0;
      while(salta){
        byte [] buff=new byte[DGRAM_BUFF_LEN];
        DatagramPacket recv=new DatagramPacket(buff,buff.length);
        socket.receive(recv);
        System.out.println("Host remoto: "+recv.getAddress());
        System.out.println("Puerto: "+recv.getPort());
        //Aqui no se entienden los datos
        byte data=recv.getData();
        System.out.println("Datos recibidos: "+new String(data));
      }//while
    }catch(IOException e){
      e.printStackTrace();
      System.exit(2);
    }
  }//run

  public static void main(String []args){
    try{
      MulticastClient2 mc2=new MulticastClient2();
      mc2.start();//Hilo
    }catch(Exception e){
      e.printStackTrace();
    }
  }//main
}//class
