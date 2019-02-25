import java.io.Serializable;

public class Usuario implements Serializable{
  String nombre;
  String aPaterno;
  String aMaterno;

  transient String pwd;//Envía este dato pero vacío
  int edad;

  public Usuario(String nombre, String aPaterno, String aMaterno, String pwd, int edad){
    this.nombre=nombre;
    this.aPaterno=aPaterno;
    this.aMaterno=aMaterno;
    this.pwd=pwd;
    this.edad=edad;
  }

  public String getNombre(){
    return nombre;
  }

  public String getAPaterno(){
    return aPaterno;
  }

  public String getAMaterno(){
  return aMaterno;
  }

  public String getPwd(){
    return pwd;
  }

  public int getEdad(){
    return edad;
  }
}
