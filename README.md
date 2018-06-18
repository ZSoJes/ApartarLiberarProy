# Programa simple de administración de material escolar

El programa fue construido en las siguientes herramientas de java:

* [java version "1.8.0_172"](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [NetBeans 8.2](https://netbeans.org/)

## Para usar el ejecutable Descargar la carpeta Dist

> Dentro encontrara:

* Proyector.jar             : programa
* Lib                       : librerias
* src/proyector/reportes    : reportes
* src/proyector/dataBase    : sql de base de datos
* src/imagenes              : imagenes que utiliza el programa

## Para usar el ejecutable en linux Descargar la carpeta Dist y mover a la carpeta con el nombre del usuario /home/userName/

> instalar las siguientes herramientas
```
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java8-installer
sudo apt-get install oracle-java8-set-default
sudo apt-get install default-jre
```

Tambien sera necesario cree un archivo con la extencion sh para una correcta ejecución este archivo se le deberá asignar los permisos de ejecución el comando recomendado es:
```
sudo chmod 764 ejecutable.sh
```

En el archivo deberá indicar los siguientes comandos (recuerde indicar la ubicación del archivo jar):
```
#!/bin/bash
cd $HOME/Documentos/ProyectorADM
java -jar ./Proyector.jar
```