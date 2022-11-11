package br.inatel.projects.quotation.management.form;

/**
 * Data loaded from the Notification call
 * 
 * @author carlos.magalhaes
 * @since 11/10/2022
 */
public class NotificationForm {

	private String host;
	private String port;

	public NotificationForm(String host, String port) {
		this.host = host;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
}