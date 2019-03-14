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
  char send_str[MAX_LEN+1];
  struct sockaddr_in mc_addr;
  unsigned int send_len;
  char * mc_addr_str;
  unsigned short mc_port;
  unsigned char mc_ttl=1;//tiempo de vida
  /*Validamos numero de argumentos*/
  if(argc!=3){
    fprintf(stderr, "Uso: %s <Multicast IP><puerto>\n", argv[0]);
    exit(1);
  }
  mc_addr_str=argv[1];
  mc_port=atoi(argv[2]);
  /*Validamos el numero de puerto*/
  if((mc_port<MIN_PORT)||(mc_port>MAX_PORT)){
    fprintf(stderr,"Numero de puerto invalido\n");
    exit(1);
  }
  /*Creamos el socket*/
  if((sock=socket(PF_INET,SOCK_DGRAM,IPPROTO_UDP))<0){
    perror("Error en el socket");
    exit(1);
  }
  /*Asignamos el valor de TTL a 1*/
  if((setsockopt(sock,IPPROTO_IP,IP_MULTICAST_TTL,(void*)&mc_ttl,sizeof(mc_ttl)))<0){
    perror("Error en setsockopt ttl");
    exit(1);
  }
  /*Construimos la estructura de dirección multicast*/
  memset(&mc_addr,0,sizeof(mc_addr));
  mc_addr.sin_family=AF_INET;
  mc_addr.sin_addr.s_addr=inet_addr(mc_addr_str);
  mc_addr.sin_port=htons(mc_port);
  /*Leemos texto a enviar*/
  printf("Comience a escribir (return para enviar ctrl_C para salir)\n");
  /*Limpiamos el buffer*/
  memset(send_str,0,sizeof(send_str));
  while (fgets(send_str,MAX_LEN,stdin)) {
    send_len=strlen(send_str);
    /*Enviamos el texto al canal de multicast*/
    if((sendto(sock,send_str,send_len,0,(struct sockaddr*)&mc_addr,sizeof(mc_addr)))!=send_len){
      perror("Sentdto() envió un número incorecto de bytes");
      exit(1);
    }
    /*Limpiamos el buffer para el siguiente envío*/
    memset(send_str,0,sizeof(send_str));
  }
  close(sock);
  exit(0);
}
