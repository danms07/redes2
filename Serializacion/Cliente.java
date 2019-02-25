import java.net.*;
import java.io.*;

public class Cliente{
  public static void main(String []args){
    ObjectOutputStream oos;
    ObjectInputStream ois;

    String host="127.0.0.1";
    int port=9999;
    try{
      Socket cl=new Socket(host,port);
      System.out.println("Conexión establecida");
      oos=new ObjectOutputStream(cl.getOutputStream());
      ois=new ObjectInputStream(cl.getInputStream());
      Usuario u=new Usuario("Pepito","Perez","Juarez","12345",20);
      System.out.println("Enviando objeto");
      oos.writeObject(u);
      oos.flush();
      System.out.println("Preparado para recibir respuesta");
      Usuario u2=(Usuario)ois.readObject();
      System.out.println("Objeto recibido  ...Extrayendo información");
      System.out.println("Nombre "+u2.getNombre());
      System.out.println("A. Paterno "+u2.getAPaterno());
      System.out.println("A. Materno "+u2.getAMaterno());
      System.out.println("Password "+u2.getPwd());
      System.out.println("Edad "+u2.getEdad());
      cl.close();
    }catch(Exception e){
      e.printStackTrace();
    }
  }
}
