
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Esta clase define el comportamiento logico que debe tener la consola para la correcta ejecución del programa
 * 
 * @author Adrian Tomas Barcelo
 * @version 1.0
 * 
 */

public class Consola {
	ArrayList<Proceso> listaProcesos = new ArrayList<Proceso>();

	public Consola() {
	}
	/**
	 * Logica principal del programa. Mediante runtime y bufferedReader ejecutamos el 
	 * comando introducido y se muestra la salida standar o en su defecto el error durante la ejecucion del programa
	 * 
	 * @param input
	 */
	public void ejecutarComando(String input) {

		Runtime r = Runtime.getRuntime();
		Process p;

		try {
			p = r.exec(input);
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String linea;
			while ((linea = stdInput.readLine()) != null) {
				System.out.println(linea);
			}

			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			while ((linea = stdError.readLine()) != null) {
				System.out.println(linea);
			}

			int exitCode = p.waitFor();

			Proceso newProceso = new Proceso(System.getProperty("user.name"), input, p.pid(), exitCode,
					LocalDateTime.now());

			listaProcesos.add(newProceso);

		} catch (Exception ex) {
			System.out.println("Error en el comando: " + input);
			
		}

	}

}
