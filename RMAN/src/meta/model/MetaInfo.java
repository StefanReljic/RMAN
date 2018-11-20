package meta.model;

import java.io.Serializable;

public class MetaInfo implements Serializable {

	private String user;
	private String password;
	private String host;
	private int port;
	private String resourceId;

	/**
	 * Fill only for not database information resources.
	 */
	private String fullResourcePath;

	public MetaInfo() {

	}

	public MetaInfo(String user, String password, String host, int port, String resourceId, String fullResourcePath) {
		super();
		this.user = user;
		this.password = password;
		this.host = host;
		this.port = port;
		this.resourceId = resourceId;
		this.fullResourcePath = fullResourcePath;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getFullResourcePath() {
		return fullResourcePath;
	}

	public void setFullResourcePath(String fullResourcePath) {
		this.fullResourcePath = fullResourcePath;
	}

}
