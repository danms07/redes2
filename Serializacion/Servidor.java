import java.net.*;
import java.io.*;


public class Servidor{
  public static void main(String []args){
    ObjectOutputStream oos;
    ObjectInputStream ois;

    try{
      ServerSocket s=new ServerSocket(9999);
      System.out.println("Servidor iniciado");
      for(;;){
        Socket cl=s.accept();
        System.out.println("Cliente conectado desde "+cl.getInetAddress()+":"+cl.getPort());
        oos=new ObjectOutputStream(cl.getOutputStream());
        ois=new ObjectInputStream(cl.getInputStream());
        Usuario u=(Usuario)ois.readObject();
        System.out.println("Objeto recibido  ...Extrayendo información");
        System.out.println("Nombre "+u.getNombre());
        System.out.println("A. Paterno "+u.getAPaterno());
        System.out.println("A. Materno "+u.getAMaterno());
        System.out.println("Password "+u.getPwd());
        System.out.println("Edad "+u.getEdad());
        System.out.println("Devolvienndo objeto...");
        oos.writeObject(u);
        oos.flush();
        cl.close();
      }
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}
