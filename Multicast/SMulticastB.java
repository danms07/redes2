import java.net.*;

public class SMulticastB{
  public static void min(String []args){
    InetAddress gpo;
    try{
      MulticastSocket s=new MulticastSocket(9876);
      s.sertReuseAddress(true);
      s.setTimeToLive(1);
      String msj="Hola";
      byte []b=msj.getBytes();
      gpo=InetAddress.getByName("228.1.1.1");
      s.joinGroup("gpo");
      for(;;){
        DatagramPacket p=new DatagramPacket(b,b.length(,gpo,9999));
        s.send(p);
        System.out.println("Mensaje "+msj+" con TTL: "+s.getTimeToLive());
        try{
          Thread.sleep(3000);
        }catch(InterruptedException e){
          e.printStackTrace();
        }
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}
