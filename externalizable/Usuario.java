import java.util.*;
import java.io.*;

class Usuario implements Externalizable{
  private String usuario;
  private String password;
  public Usuario(){
    System.out.println("Creando usuario");
  }

  Usuario(String u, String p){
    usuario=u;
    password=p;
    System.out.println("Creando usuario "+u+", "+p);
  }

  public void writeExternal(ObjectOutput out) throws IOException{
    out.writeObject(usuario);
  }

  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException{
    System.out.println("Usuario.readExternal");

    //Explicitamente indicamos cuales atributos se van a recuperar

    usuario=(String)in.readObject();
  }

  public void muestraUsuario(){
    String cad="Usuario: "+usuario+" Password: ";
    if(password.equals(null)){
      cad=cad+"No disponible";
    }
    else{
      cad=cad+password;
    }
    System.out.println(cad);
  }
}

class ListaUsuarios implements Serializable{
  private LinkedList lista=new LinkedList();
  int valor;

  ListaUsuarios(String[] usuarios,String[] passwords){
    for(int i=0;i<usuarios.length;i++){
      lista.add(new Usuario(usuarios[i],passwords[i]));
    }
  }

  public void MuestraUsuarios(){
    ListIterator li=lista.listIterator();
    Usuario u;

    while(li.hasNext()){
      u=(Usuario)li.next();
      u.muestraUsuario();
    }
  }
}
