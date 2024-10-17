
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Tarea implements Runnable {

    private int id;
    private LocalTime horaEjecucion;
    private String comando;
    private Status status;
    private boolean running;
    private LocalDate ultimaEjecucion;

    /**
     * Constructor
     * 
     * @param id
     * @param horaEjecucion
     * @param comando
     * @param status
     * @param running
     * @param ultimaEjecucion
     */
    public Tarea(int id, LocalTime horaEjecucion, String comando) {
        this.id = id;
        this.horaEjecucion = horaEjecucion;
        this.comando = comando.trim();
        this.status = Status.Pendiente;
        this.running = true;
        this.ultimaEjecucion = LocalDate.now();
    }
    /**
     * Metodo que cuando el proceso esta corriendo comprueba que es su hora 
     * calcula el tiempo de espera y si es su hora especificada en el archivo de texto 
     * realiza la ejecucion con la ayuda del siguiente metodo
     */
    @Override
    public void run() {
        while (running) {
            
                try {
                    LocalDateTime now = LocalDateTime.now();
                    LocalDateTime siguienteEjecucion = LocalDateTime.of(now.toLocalDate(), horaEjecucion);
                    if (now.isAfter(siguienteEjecucion)) {
                        siguienteEjecucion.plusDays(1);
                    }

                    Duration tiempoAntesEjecucion = Duration.between(now, siguienteEjecucion);
                    long sleepTime = tiempoAntesEjecucion.toMillis();

                    if (sleepTime > 0) {
                        Thread.sleep(sleepTime);
                    } else {
                        Thread.sleep(1000); // Esperar un segundo si el cálculo resulta en un tiempo negativo
                    }

                    now = LocalDateTime.now();
                    if (running && status == Status.Pendiente &&
                            now.toLocalDate().isAfter(ultimaEjecucion) &&
                            now.toLocalTime().isAfter(horaEjecucion)) {
                        executeCommand();
                        ultimaEjecucion = now.toLocalDate();
                    }

                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                
            }
        }

    }
    /*
     * Metodo encargado de ejecutar el comando especificado en el documento de texto mediante ProcessBuilder
     */
    private void executeCommand() {

        if (comando == null || comando.isEmpty()) {
            System.err.println("Error: Comando vacío en la tarea" + id);
        }
        System.out.println("Ejecutando el comando programado: " + comando);
        try {
            String[] cmdArrayList = comando.split("\\s+");
            ProcessBuilder pb = new ProcessBuilder(cmdArrayList);
            Process process = pb.start();
            int exitCode = process.waitFor();
            System.out.println("Salida del comando: " + exitCode);
        } catch (Exception e) {
            System.out.println("Error en el comando: " + e.getMessage());
        }
    }

    /**
     * Metodo que cancela la tarea seleccionada
     */
    public void cancel() {
        this.status = Status.Cancelado;
        this.running = false;
    }

    /**
     * Obtiene y devuelve el tiempo que queda para que 
     * se ejecute el comando de nuevo segun la hora a la que se ha ejecutado
    * @return
     */
    public String getTiempoRestante() {
        LocalTime now = LocalTime.now();
        LocalTime siguienteEjecucion = horaEjecucion;

        if (now.isAfter(siguienteEjecucion)) {
            siguienteEjecucion.plusHours(24);
        }

        Duration timeUntilExecution = Duration.between(now, siguienteEjecucion);
        long hours = timeUntilExecution.toHours();
        long minutes = timeUntilExecution.toMinutesPart();
        long seconds = timeUntilExecution.toSecondsPart();

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    /**
     * Getters y setters
     */
    public enum Status {
        Pendiente, Cancelado
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getHoraEjecucion() {
        return horaEjecucion;
    }

    public void setHoraEjecucion(LocalTime horaEjecucion) {
        this.horaEjecucion = horaEjecucion;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public LocalDate getUltimaEjecucion() {
        return ultimaEjecucion;
    }

    public void setUltimaEjecucion(LocalDate ultimaEjecucion) {
        this.ultimaEjecucion = ultimaEjecucion;
    }

}