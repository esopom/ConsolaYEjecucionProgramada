import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Tarea> tareas = new ArrayList<>();
    private static String fileNme = "/home/adrian/ConsolaYEjecucionProgramada/src/psptab.txt";

    public static void main(String[] args) {
        readFile(fileNme);
        startTareas();
        modoConsola();
    }

    /**
     * Mediante el nombre del archivo, lo le y recoge la imformacion sobre los
     * comandos
     * que hay escritos asi como la hora en la que deben ser ejecutados para
     * posteriormente añadirlo
     * esta informacion a la lista de tareas
     * 
     * @param fileNme
     */
    private static void readFile(String fileNme) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileNme))) {
            String linea;
            int numeroLinea = 0;
            while ((linea = br.readLine()) != null) {
                numeroLinea++;
                try {
                    String[] partes = linea.split("\\s+", 3);
                    int hora = Integer.parseInt(partes[0]);
                    int minutos = Integer.parseInt(partes[1]);
                    String comando = partes[2].trim();
                    if (comando.isEmpty()) {
                        throw new IllegalArgumentException("Comando vacío");
                    }
                    tareas.add(new Tarea(tareas.size(), LocalTime.of(hora, minutos, minutos), comando));
                } catch (Exception e) {
                    System.out.println("Error al leer linea: " + numeroLinea);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    /**
     * Comienza en un hilo las tareas que hay en la lista de tareas
     */
    private static void startTareas() {
        for (Tarea tarea : tareas) {
            Thread t = new Thread(tarea);
            t.start();
        }
    }
    /**
     * Permite ejecutar comandos e interactuar como si fuera 
     * una consola de comandos Linux con ayuda de las clases Consola
     * y Proceso que se implementaron en la anterior practica entregada
     */
    private static void modoConsola() {
        Consola con = new Consola();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Indica lo que quieres hacer: tareas, finalizar xx o salir. > ");
            String command = sc.nextLine().trim().toLowerCase();

            if (command.equals("tareas")) {
                showTareas();
            } else if (command.equals("finalizar")) {
                System.out.println("Escriba el id de la tarea a finalizar: ");
                int id = sc.nextInt();
                terminarTarea(id);
            } else if (command.equals("salir")) {
                terminarPrograma();
            } else {
                con.ejecutarComando(command);
            }

        }
    }
    /**
     * Cancela todas las tareas y sale del programa
     */
    private static void terminarPrograma() {
        for (Tarea tarea : tareas) {
            tarea.cancel();
        }
        System.out.println("Cerrando el programa...");
        System.exit(0);
    }
    /**
     * Busca la tarea mediante el id pasado por parametro
     * comprueba cual de las tareas tiene el mismo id y la cancela mediante el 
     * metodo implementado en la clase Tarea
     * @param id
     */
    private static void terminarTarea(int id) {
       for (Tarea tarea : tareas) {
            if(tarea.getId()==id){
                tarea.cancel();
                System.out.println("Tarea cancelada con exito");
                break;
            }else System.out.println("Error al eliminar la tarea con id: "+tarea.getId());
       }
    }

    /**
     * Metodo auxiliar con el que podemos ver la tareas que hay pendientes o
     * canceladas
     */
    private static void showTareas() {
        int tareasPendientes = 0;
        int tareasCanceladas = 0;

        for (Tarea t : tareas) {
            if (t.getStatus() == Tarea.Status.Pendiente) {
                tareasPendientes++;
            } else if (t.getStatus() == Tarea.Status.Cancelado) {
                tareasCanceladas++;
            }
        }

        System.out.println("Hay un total de " + tareas.size() + " tareas, " + tareasPendientes + //
                " tareas pendientes y " + tareasCanceladas + " canceladas");
        // Intentamos alinear la informacion
        System.out.println("ID | HORA EJECUCION | TIEMPO RESTANTE | COMANDO | ESTADO");
        for (Tarea t : tareas) {
            System.out.printf("%d  |  %s         |   %s    | %s   |  %s%n",
                    t.getId(),
                    t.getHoraEjecucion(),
                    t.getStatus() == Tarea.Status.Pendiente ? t.getTiempoRestante() : "********",
                    t.getComando(),
                    t.getStatus());
        }

    }

}
