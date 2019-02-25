import java.io.*;
import java.util.*;

class DemoExternalizable{
  public static void main(String []args) throws IOException,ClassNotFoundException{
    System.out.println("Creando el objeto");
    String[] usuarios={"A","B","C"};
    String[] passwords={"1","2","3"};
    ListaUsuarios lp=new ListaUsuarios(usuarios,passwords);
    ObjectOutputStream o=new ObjectOutputStream(new FileOutputStream("objetos.out"));
    o.writeObject(lp);
    o.close();

    System.out.println("Recuperando objeto");
    ObjectInputStream in=new ObjectInputStream(new FileInputStream("Objetos.out"));
    ListaUsuarios l2=(ListaUsuarios)in.readObject();
    l2.MuestraUsuarios();
  }
}
