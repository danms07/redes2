import java.net.*;

public class CMulticastB{
  public static void main(String []args){
    InetAddress gpo;
    try{
      MulticastSocket cl=new MulticastSocket(9999);
      System.out.println("Cliente escuchando puerto "+cl.getLocalPort());
      cl.sertReuseAddress(true);
      try{
        gpo=InetAddress.getByName("228.1.1.1");
      }catch(UnknownHostException u){
        System.out.println("Dirección erronea");
      }
      cl.joinGroup(gpo);
      System.out.println("Unido al grupo");
      for(;;){
        DatagramPacket p=new DatagramPacket(new byte[10],10);
        cl.receive(p);
        String msj=new String(p.getData());
        System.out.println("Datagrama recibido: "+msj);
        System.out.println("Servidor descubierto: "+p.getAddress()+":"+p.gtPort());
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}
