#include <sys/types.h>//Definición de tipos
#include <sys/socket.h>//Llamadas API socket
#include <netinet/in.h>//Estructura de datos
#include <arpa/inet.h>//sockaddr_in
#include <stdio.h>//printf
#include <stdlib.h>//atoi
#include <string.h>//stdlen
#include <unistd.h>//close

#define MAX_LEN 1024
#define MIN_PORT 1024
#define MAX_PORT 65535

int main(int  argc, char *argv[]){
  int sock;//descriptor de socket
  int flag_on=1;//Banderas de opción
  struct sockaddr_in mc_addr;//Dirección
  char recv_str[MAX_LEN+1];//Buffer lectura
  int recv_len;//Longitud de cadena recibida
  struct ip_mreq mc_req;//Estructura solicitud mc
  char *mc_addr_str;//Dirección IP multicast
  unsigned short mc_port;//Puerto multicast
  struct sockaddr_in from_addr;//paquete origen
  unsigned int from_len;//Longitud Dirección origen
  /*Validación de parámetros*/
  if(argc!=3){
    fprintf(stderr,"Uso %s <Multicast IP> <Multicast puerto>\n",argv[0]);
    exit(1);
  }
  mc_addr_str=argv[1];
  mc_port=atoi(argv[2]);
  /*Validamos el número de puerto*/
  if((mc_port<MIN_PORT)||(mc_port>MAX_PORT)){
    fprintf(stderr,"Numero de puerto inválido %d\n",mc_port);
    exit(1);
  }
  /*Se crea un socket para un canal Multicast*/
  if((sock=socket(PF_INET,SOCK_DGRAM,IPPROTO_UDP))<0){
    perror("Error en creación del socket");
    exit(1);
  }
  /*Permite la reutilización del socket*/
  if((setsockopt(sock,SOL_SOCKET,SO_REUSEADDR,&flag_on,sizeof(flag_on)))<0){
    perror("Error en setsockopt");
    exit(1);
  }
  /*Se construye la estructura sockaddr*/
  memset(&mc_addr,0,sizeof(mc_addr));
  mc_addr.sin_family=AF_INET;
  mc_addr.sin_addr.s_addr=htons(INADDR_ANY);
  mc_addr.sin_port=htons(mc_port);
  /*Liga la dirección con el socket*/
  if((bind(sock,(struct sockaddr *)&mc_addr,sizeof(mc_addr)))<0){
    perror("Error en el bind");
    exit (1);
  }
  /*Estructura para unirse al grupo de multicast*/
  mc_req.imr_multiaddr.s_addr=inet_addr(mc_addr_str);
  mc_req.imr_interface.s_addr=htons(INADDR_ANY);
  /*Se une al grupo mediante setsockopt*/
  if((setsockopt(sock,IPPROTO_IP,IP_ADD_MEMBERSHIP,(void *)&mc_req,sizeof(mc_req)))<0){
    perror("Error en setsockopt(), membership");
    exit(1);
  }
  for(;;){//Lazo infinito
    //Se limpia el buffer y la estructura de lectura
    memset(recv_str,0,sizeof(recv_str));
    from_len=sizeof(from_addr);
    memset(&from_addr,0,from_len);
    /*Bloqueo para la recepción de paquetes*/
    if((recv_len=recvfrom(sock,recv_str,MAX_LEN,0,(struct sockaddr *)&from_addr,&from_len))<0){
      perror("Error al recibir un paquete");
      exit(1);
    }
    /*Imprimimos lo que se recibió*/
    printf("\nSe recibieron %d bytes desde %s:",recv_len,inet_ntoa(from_addr.sin_addr));
    printf("\n%s\n",recv_str);
  }//for
  /*En caso de modificar el lazo infinito para salir*/
  /*Modificamos la opción del socket para salir del grupo*/
  if((setsockopt(sock,IPPROTO_IP,IP_DROP_MEMBERSHIP,(void*)&mc_req,sizeof(mc_req)))<0){
    perror("Error en setsockopt drop membership");
    exit (1);
  }
  close(sock);
}
