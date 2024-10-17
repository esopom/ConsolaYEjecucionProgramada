# Programador de Tareas

## Descripción
Este programa es un planificador de tareas simple desarrollado en Java. Permite a los usuarios programar la ejecución de comandos en momentos específicos del día, similar a la funcionalidad de cron en sistemas Unix.

## Características
- Lee tareas programadas desde un archivo de configuración.
- Ejecuta comandos automáticamente a las horas especificadas.
- Interfaz de línea de comandos para gestionar tareas.
- Permite ver tareas programadas, cancelar tareas y salir del programa.

## Requisitos
- Java Development Kit (JDK) 8 o superior

## Configuración
1. Crea un archivo llamado `psptab.txt` en el mismo directorio que el programa.
2. En cada línea del archivo, especifica una tarea con el siguiente formato:
   ```
   HH MM comando
   ```
   Donde:
   - `HH` es la hora en formato 24 horas (00-23)
   - `MM` son los minutos (00-59)
   - `comando` es el comando a ejecutar

Ejemplo de `psptab.txt`:
```
10 00 ls -l
15 30 echo "Hola Mundo"
```

## Compilación
Para compilar el programa, ejecuta el siguiente comando en la terminal:

```
javac Main.java Task.java
```

## Ejecución
Para ejecutar el programa, usa:

```
java Main
```

## Uso
Una vez que el programa esté en ejecución, podrás usar los siguientes comandos:

- `tareas`: Muestra todas las tareas programadas y su estado.
- `finalizar xx`: Cancela la tarea con el ID xx.
- `salir`: Termina el programa.

## Notas
- El programa debe mantenerse en ejecución para que las tareas se ejecuten a las horas programadas.
- Las tareas se ejecutan una vez al día a la hora especificada.
- Si el programa se cierra, todas las tareas programadas se perderán y deberán ser reiniciadas al volver a ejecutar el programa.

## Solución de problemas
Si las tareas no se ejecutan como se espera:
1. Verifica que el archivo `psptab.txt` esté correctamente formateado.
2. Asegúrate de que los comandos especificados sean válidos y ejecutables en tu sistema.
3. Comprueba los logs del programa para ver si hay errores o mensajes informativos.

## Contribuciones
Las contribuciones son bienvenidas. Por favor, abre un issue para discutir cambios mayores antes de enviar un pull request.

## Licencia
Este proyecto está licenciado bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.
