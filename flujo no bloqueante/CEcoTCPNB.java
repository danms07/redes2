import java.nio.channels.*;
import java.io.*;
import java.net.*;
import java.util.Iterator;

public class CEcoTCPNB{
  public static void main(String []args){
    try{
      String dir="127.0.0.1";
      int pto=9999;
      ByteBuffer b1=null;
      ByteBuffer b2=null;
      InetSocketAddress dst=new InetSocketAddress(dir,pto);
      SocketChannel cl=SocketChannel.open();
      BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
      cl.configureBlocking(false);
      Selector sel=Selector.open();
      cl.register(sel,SelectionKey.OP_CONNECT);
      cl.connect(dst);
      while(true){
        sel.select();
        Iterator <SelectionKey> it=sel.selecteedKeys().iterator();
        while(it.hasNext()){
          SelectionKey k=(SelectionKey)it.next();
          it.remove();
          if(k.isConnectable()){
            SocketChannel ch=(SocketChannel)k.channel();
            if(ch.isConnectionPending()){//Considerar el cliente
              System.out.println("Estableciendo conexión... espere");
              try{
                ch.finishConnect();
              }catch(Exception e){
                e.printStackeTrace();
              }
              System.out.println("Conexión establecida");
              System.out.println("Escriba texto <Enter> para enviar");
              System.out.println("SALIR para terminar");
            }
            ch.register(sel,SelectionKey.OP_READ|SelectionKey.OP_WRITE);
            continue;
          }
          if(k.isReadable()){
            SocketChannel ch=(SocketChannel) k.channel();
            b1=ByteBuffer.allocate(2000);
            b1.clear();
            int n=ch.read(b1);
            b1.flip();
            String eco=new String(b1.array(),0,n);
            System.out.println("Eco de "+n+" Bytes recibido: "+eco);
            k.interestOps(SelectionKey.OP_WRITE);
            continue;
          }
          else if(k.isWritable()){
            SocketChannel ch=(SocketChannel) k.channel();
            String datos="";
            datos=br.readLine();
            if(datos.equalsIgnoreCase("SALIR")){
              System.out.println("Termina aplicación");
              byte []mm="SALIR".getBytes();
              b2=ByteBuffer.wrap(mm);
              ch.write(b2);
              k.interestOps(SelectionKey.OP_READ);
              k.cancel();
              ch.close();
              System.exit(0);
            }
            byte []mm=datos.getBytes();
            System.out.println("Enviando eco de "+mm.length+" bytes");
            b2=ButeBuffer.wrap(mm);
            ch.write(b2);
            k.interestOps(SelectionKey.OP_READ);
            continue;
          }
        }
      }
    }
    catch(Exception e){
      e.printStackeTrace();
    }
  }
}
