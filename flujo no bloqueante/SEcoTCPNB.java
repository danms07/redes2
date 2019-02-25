

public class SEcoTCPNB{
  public staticvoid main(String []args){
    try{
      String EECO="";
      int pto=9999;
      ServerSocketChannel s=ServerSocketChannel.open();
      s.configureBlocking(false);
      s.socket.bind(new InetSocketAddress(pto));
      System.out.println("Esperando clientes");
      Selector sel=Selector.open();
      s.register(sel,SelectorKey.OP_ACCEPT);
      while(true){
        sel.selector()://Bloqueo
        Iterator<SelectionKey> it=sel.selectedKeys().iterator();
        while(it.hasNext()){
          SelectionKey k=(SelectionKey) it.next();
          it.remove();
          if(k.isAcceptable()){
            SocketChannel cl=s.accept();
            System.out.println("Cliente conectado desde "+cl.socket.getInetAddress()+":"+cl.socket.getPort());
            cl.configureBlocking(false);
            cl.register(sel,SelectionKey.OP_READ|SelectionKey.OP_WRITE);
            continue;
          }
          if(k.isReadable()){
            try{
              SocketChannel ch=(SocketChannel)k.channel();
              ByteBuffer b=ByteBuffer.allocate(2000);
              b.clear();
              int n=0;
              String msj;
              n=ch.read(b);
              b.flip();
              if(n>0) msj=new String(b.array,0,n);
              System.out.println("Mensaje de "+n+" bytes recibidos: "+msj);
              if(msj.equalsIgnoreCase("SALIR")){
                k.interestOps(SelectionKey.OP_WRITE);
                ch.close();
              }
              else{
                EECO="Eco ->"+msj;
                k.interestOps(SelectionKey.OP_WRITE);
              }
            }catch(IOException io){

            }
            contiue;
          }
          else if(k.isWritable()){
            try{
              SocketChannel ch=(SocketChannel)k.channel();
              ByteBuffer bb=ByteBuffer.wrap(EECO.getBytes());
              ch.write(bb);
              System.out.println("Mensaje de "+EECO.length()+" bytes enviados:"+EECO);
              EECO="";
            }
            catch(IOException io){

            }
            k.interestOps(SelectionKey.OP_READ);
            continue;
          }
        }
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}
