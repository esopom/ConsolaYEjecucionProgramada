

import java.time.LocalDateTime;

/**
 *Clase en la cual almacenaremos en un objeto las caracteristicas que nos interesan de un proceso
 */

public class Proceso {
	private String usuario;
	private String comando;
	private long pid;
	private int salida;
	private LocalDateTime fechaInicio;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getComando() {
		return comando;
	}

	public void setComando(String comando) {
		this.comando = comando;
	}

	public long getPid() {
		return pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public int getSalida() {
		return salida;
	}

	public void setSalida(int salida) {
		this.salida = salida;
	}

	public LocalDateTime getInicio() {
		return fechaInicio;
	}

	public void setInicio(LocalDateTime inicio) {
		this.fechaInicio = inicio;
	}

	public Proceso(String usuario, String comando, long pid, int salida, LocalDateTime inicio) {
		super();
		this.usuario = usuario;
		this.comando = comando;
		this.pid = pid;
		this.salida = salida;
		this.fechaInicio = inicio;
	}

}
