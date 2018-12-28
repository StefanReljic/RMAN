package meta.model;

import java.io.Serializable;

public class MetaInfo implements Serializable {

	private static final long serialVersionUID = -566939855607504901L;

	private String user;
	private String password;
	private String host;
	private int port;
	private String resourceId;
	private String type;

	public MetaInfo() {

	}

	public MetaInfo(String user, String password, String host, int port, String resourceId) {
		super();
		this.user = user;
		this.password = password;
		this.host = host;
		this.port = port;
		this.resourceId = resourceId;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}